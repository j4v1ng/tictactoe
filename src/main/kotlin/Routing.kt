package com.javing

import com.javing.models.GameMode
import com.javing.models.TicTacToeSession
import com.javing.services.GameService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import io.ktor.server.thymeleaf.*
import java.util.UUID

fun Application.configureRouting(gameService: GameService) {
    routing {
        // Home page route
        get("/") {
            // Check if session exists, create one if not
            if (call.sessions.get<TicTacToeSession>() == null) {
                call.sessions.set(TicTacToeSession(UUID.randomUUID().toString()))
            }

            // Get session ID
            val session = call.sessions.get<TicTacToeSession>()

            // Get game state and prepare model for the template
            val model = mutableMapOf<String, Any>()

            if (session != null) {
                val gameState = gameService.getGameState(session.id)
                model["gameState"] = gameState
            }

            // Render the game page
            call.respond(ThymeleafContent("game", model))
        }

        // API route for making a move
        post("/api/move") {
            // Get session ID
            val session = call.sessions.get<TicTacToeSession>()
            if (session == null) {
                call.respond(HttpStatusCode.BadRequest, "No session found")
                return@post
            }

            // Parse move data
            val moveData = call.receiveParameters()
            val row = moveData["row"]?.toIntOrNull()
            val col = moveData["col"]?.toIntOrNull()

            if (row == null || col == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid move data")
                return@post
            }

            // Make the move
            val success = gameService.makeMove(session.id, row, col)

            // Get updated game state
            val gameState = gameService.getGameState(session.id)

            // Respond with the updated game state
            call.respond(gameState)
        }

        // API route for starting a new game
        post("/api/new-game") {
            // Get session ID
            val session = call.sessions.get<TicTacToeSession>()
            if (session == null) {
                call.respond(HttpStatusCode.BadRequest, "No session found")
                return@post
            }

            // Start a new game
            gameService.newGame(session.id)

            // Get updated game state
            val gameState = gameService.getGameState(session.id)

            // Respond with the updated game state
            call.respond(gameState)
        }

        // API route for getting the current game state
        get("/api/game-state") {
            // Get session ID
            val session = call.sessions.get<TicTacToeSession>()
            if (session == null) {
                call.respond(HttpStatusCode.BadRequest, "No session found")
                return@get
            }

            // Get game state
            val gameState = gameService.getGameState(session.id)

            // Respond with the game state
            call.respond(gameState)
        }

        // API route for setting the game mode
        post("/api/set-game-mode") {
            // Get session ID
            val session = call.sessions.get<TicTacToeSession>()
            if (session == null) {
                call.respond(HttpStatusCode.BadRequest, "No session found")
                return@post
            }

            // Parse mode data
            val modeData = call.receiveParameters()
            val modeStr = modeData["mode"]

            if (modeStr == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid mode data")
                return@post
            }

            // Convert string to GameMode enum
            val mode = when (modeStr) {
                "PLAYER_VS_COMPUTER" -> GameMode.PLAYER_VS_COMPUTER
                "PLAYER_VS_PLAYER" -> GameMode.PLAYER_VS_PLAYER
                else -> {
                    call.respond(HttpStatusCode.BadRequest, "Invalid mode: $modeStr")
                    return@post
                }
            }

            // Set the game mode
            gameService.setGameMode(session.id, mode)

            // Get updated game state
            val gameState = gameService.getGameState(session.id)

            // Respond with the updated game state
            call.respond(gameState)
        }
    }
}

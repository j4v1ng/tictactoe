package com.javing.services

import com.javing.models.Game
import com.javing.models.GameMode
import com.javing.models.GameState
import java.util.concurrent.ConcurrentHashMap

/**
 * Service for managing game instances.
 * This service is responsible for creating, retrieving, and managing game instances for different sessions.
 */
class GameService {
    // Map of session ID to game instance
    private val games = ConcurrentHashMap<String, Game>()

    /**
     * Gets or creates a game for the given session ID.
     * @param sessionId The session ID
     * @return The game instance
     */
    fun getOrCreateGame(sessionId: String): Game {
        return games.getOrPut(sessionId) { Game() }
    }

    /**
     * Makes a player move for the given session ID.
     * @param sessionId The session ID
     * @param row The row index (0-2)
     * @param col The column index (0-2)
     * @return true if the move was successful, false otherwise
     */
    fun makeMove(sessionId: String, row: Int, col: Int): Boolean {
        val game = getOrCreateGame(sessionId)
        return game.makePlayerMove(row, col)
    }

    /**
     * Gets the game state for the given session ID.
     * @param sessionId The session ID
     * @return The game state
     */
    fun getGameState(sessionId: String): GameState {
        val game = getOrCreateGame(sessionId)
        return game.getGameState()
    }

    /**
     * Starts a new game for the given session ID.
     * @param sessionId The session ID
     */
    fun newGame(sessionId: String) {
        val game = getOrCreateGame(sessionId)
        game.newGame()
    }

    /**
     * Sets the game mode for the given session ID.
     * @param sessionId The session ID
     * @param mode The game mode to set
     */
    fun setGameMode(sessionId: String, mode: GameMode) {
        val game = getOrCreateGame(sessionId)
        game.changeGameMode(mode)
    }

    /**
     * Removes the game for the given session ID.
     * @param sessionId The session ID
     */
    fun removeGame(sessionId: String) {
        games.remove(sessionId)
    }
}

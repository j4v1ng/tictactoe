package com.javing

import com.javing.models.TicTacToeSession
import com.javing.services.GameService
import io.ktor.server.application.*
import io.ktor.server.thymeleaf.Thymeleaf
import io.ktor.server.thymeleaf.ThymeleafContent
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import io.ktor.server.sessions.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.jackson.*
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.server.routing.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    // Set up Thymeleaf
    install(Thymeleaf) {
        setTemplateResolver(ClassLoaderTemplateResolver().apply {
            prefix = "templates/"
            suffix = ".html"
            characterEncoding = "utf-8"
        })
    }

    // Set up sessions
    install(Sessions) {
        cookie<TicTacToeSession>("TIC_TAC_TOE_SESSION") {
            cookie.path = "/"
            cookie.maxAgeInSeconds = 3600 // 1 hour
        }
    }

    // Set up content negotiation with Jackson
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    // Create and register the game service
    val gameService = GameService()

    // Set up routing
    configureRouting(gameService)
}

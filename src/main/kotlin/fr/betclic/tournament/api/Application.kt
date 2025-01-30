package fr.betclic.tournament.api

import fr.betclic.tournament.api.config.configureInjection
import fr.betclic.tournament.api.config.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
    configureInjection()
    configureGlobalExceptionHandler()
    // TODO 1 : add swagger (with open api for example)
}

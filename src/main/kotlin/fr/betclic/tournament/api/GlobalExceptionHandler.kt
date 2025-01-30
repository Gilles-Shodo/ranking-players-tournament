package fr.betclic.tournament.api

import fr.betclic.tournament.core.exception.PlayerAlreadyExistsException
import fr.betclic.tournament.core.exception.PlayerNotCreatedException
import fr.betclic.tournament.core.exception.PlayerNotFoundException
import fr.betclic.tournament.core.exception.PlayerNotUpdatedException
import fr.betclic.tournament.core.exception.PlayerScoreExceededException
import fr.betclic.tournament.core.exception.PlayersNotRemovedException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import java.lang.IllegalArgumentException


fun Application.configureGlobalExceptionHandler() {

    install(StatusPages) {

        exception<PlayerAlreadyExistsException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, cause.message ?: "")
        }

        exception<PlayerNotCreatedException> { call, cause ->
            call.respond(HttpStatusCode.ServiceUnavailable, cause.message ?: "")
        }

        exception<PlayerNotFoundException> { call, cause ->
            call.respond(HttpStatusCode.NotFound, cause.message ?: "")
        }

        exception<PlayerNotUpdatedException> { call, cause ->
            call.respond(HttpStatusCode.ServiceUnavailable, cause.message ?: "")
        }

        exception<PlayerScoreExceededException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, cause.message ?: "")
        }

        exception<PlayersNotRemovedException> { call, cause ->
            call.respond(HttpStatusCode.ServiceUnavailable, cause.message ?: "")
        }

        exception<IllegalArgumentException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, cause.message ?: "")
        }

        // TODO 5 : Add unit tests for api exception handler

    }
}
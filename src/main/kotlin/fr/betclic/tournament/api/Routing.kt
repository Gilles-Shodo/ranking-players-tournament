package fr.betclic.tournament.api

import fr.betclic.tournament.api.mapper.PlayerMapper
import fr.betclic.tournament.api.mapper.PlayerViewMapper
import fr.betclic.tournament.api.request.AddPlayerRequest
import fr.betclic.tournament.api.request.ChangePlayerPointsRequest
import fr.betclic.tournament.core.inbound.ManageTournament
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Application.configureRouting() {

    val manageTournament : ManageTournament by inject()

    routing {

        post("/tournament/players") {

            val addPlayerRequest = call.receive<AddPlayerRequest>()

            val newPlayer = manageTournament.addNewPlayer(PlayerMapper.from(addPlayerRequest).get())

            call.respond(
                HttpStatusCode.Created,
                PlayerViewMapper.from(newPlayer).get()
            )
        }

        patch("/tournament/players/{id}") {

            val id = getIdFromPathVariable()

            val player = call.receive<ChangePlayerPointsRequest>()

            val updatePlayer = manageTournament.updatePlayer(PlayerMapper.from(player, id).get())

            call.respond(
                HttpStatusCode.OK,
                PlayerViewMapper.from(updatePlayer).get()
            )
        }

        get("/tournament/players/{id}") {

            val id = getIdFromPathVariable()

            call.respond(
                HttpStatusCode.OK,
                PlayerViewMapper.from(manageTournament.getPlayerById(id)).get()
            )
        }

        get("/tournament/players") {

            manageTournament.getAllPlayers()

            call.respond(
                HttpStatusCode.OK,
                manageTournament.getAllPlayers().map { PlayerViewMapper.from(it).get() }.toList()
            )
        }

        delete("/tournament/players") {

            manageTournament.removeAllPlayers()

            call.respond(HttpStatusCode.NoContent)
        }

    }
}

private fun RoutingContext.getIdFromPathVariable(): Int =
    call.parameters["id"]?.toIntOrNull() ?: throw IllegalArgumentException("Player id is not valid")
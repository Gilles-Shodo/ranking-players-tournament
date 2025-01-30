package fr.betclic.tournament.database.repository

import fr.betclic.tournament.core.domain.Player
import fr.betclic.tournament.core.outbound.ManageTournamentRepository

// TODO 4 : To implement
class ManageTournamentDynamoDbRepository : ManageTournamentRepository {

    override fun addNewPlayer(playerToAdd: Player): Player {
        TODO("Not yet implemented")
    }

    override fun updatePlayer(
        playerToUpdatet: Player
    ): Player {
        TODO("Not yet implemented")
    }

    override fun getPlayerById(id: Int): Player {
        TODO("Not yet implemented")
    }

    override fun getAllPlayers(): List<Player> {
        TODO("Not yet implemented")
    }

    override fun removeAllPlayers() {
        TODO("Not yet implemented")
    }
}
package fr.betclic.tournament.core.service

import fr.betclic.tournament.core.domain.Player
import fr.betclic.tournament.core.domain.PlayerToUpdate
import fr.betclic.tournament.core.domain.PlayerWithRanking
import fr.betclic.tournament.core.domain.Tournament
import fr.betclic.tournament.core.exception.PlayerNotUpdatedException
import fr.betclic.tournament.core.inbound.ManageTournament
import fr.betclic.tournament.core.outbound.ManageTournamentRepository

class ManageTournamentService(val manageTournamentRepository: ManageTournamentRepository) : ManageTournament {

    override fun addNewPlayer(playerToAdd: Player): Player {

        val playersRetrieved = manageTournamentRepository.getAllPlayers()

        val newPlayerToAdd = Tournament.withPlayers(playersRetrieved).getNewPlayerToAdd(playerToAdd)

        return manageTournamentRepository.addNewPlayer(
            newPlayerToAdd
        )
    }

    override fun updatePlayer(playerToUpdate: Player): Player {

        val playerRetrieved = manageTournamentRepository.getPlayerById(
            playerToUpdate.id ?: throw PlayerNotUpdatedException("Player id is empty")
        )

        return manageTournamentRepository.updatePlayer(PlayerToUpdate.from(playerRetrieved).mergeWith(playerToUpdate))
    }

    override fun getPlayerById(id: Int): PlayerWithRanking {

        val tournament = Tournament.withPlayers(manageTournamentRepository.getAllPlayers())

        return tournament.getPlayer(id)
    }

    override fun getAllPlayers(): List<PlayerWithRanking> {

        val tournament = Tournament.withPlayers(manageTournamentRepository.getAllPlayers())

        return  tournament.getPlayersSortedByRanking()
    }

    override fun removeAllPlayers() {
        manageTournamentRepository.removeAllPlayers()
    }
}
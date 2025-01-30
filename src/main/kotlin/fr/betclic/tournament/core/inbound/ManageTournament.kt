package fr.betclic.tournament.core.inbound

import fr.betclic.tournament.core.domain.Player
import fr.betclic.tournament.core.domain.PlayerWithRanking

interface ManageTournament {

    fun addNewPlayer(playerToAdd: Player): Player

    fun updatePlayer(playerToUpdate: Player): Player

    fun getPlayerById(id: Int): PlayerWithRanking

    fun getAllPlayers(): List<PlayerWithRanking>

    fun removeAllPlayers()
}
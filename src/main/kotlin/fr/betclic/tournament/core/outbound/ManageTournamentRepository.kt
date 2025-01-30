package fr.betclic.tournament.core.outbound

import fr.betclic.tournament.core.domain.Player

interface ManageTournamentRepository {

    fun addNewPlayer(playerToAdd: Player): Player

    fun updatePlayer(playerToUpdate: Player): Player

    fun getPlayerById(id: Int): Player

    fun getAllPlayers(): List<Player>

    fun removeAllPlayers()
}
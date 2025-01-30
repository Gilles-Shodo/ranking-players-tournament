package fr.betclic.tournament.core.domain

import fr.betclic.tournament.core.exception.PlayerAlreadyExistsException
import fr.betclic.tournament.core.exception.PlayerNotFoundException
import java.util.concurrent.atomic.AtomicInteger

class Tournament private constructor(private val players: Map<Int?, PlayerWithRanking>) {

    companion object {
        fun withPlayers(players: List<Player>): Tournament {

            if (players.isEmpty()) {
                return Tournament(emptyMap<Int?, PlayerWithRanking>())
            }

            var previousPlayer : Player = players[0]

            val index = AtomicInteger(0)

            var playersSortedByRanking =  mutableMapOf<Int?, PlayerWithRanking>()

            players.sortedByDescending { player -> player.score?.points() }
                   .forEach { currenPlayer ->

                playersSortedByRanking.put(currenPlayer.id,
                    PlayerWithRanking(Player(
                        currenPlayer.id,
                        currenPlayer.name,
                        currenPlayer.score),
                        buildRanking(currenPlayer, previousPlayer, index)
                    ))

                previousPlayer = currenPlayer

             }

            return Tournament(playersSortedByRanking)
        }

        private fun buildRanking(currenPlayer: Player, previousPlayer: Player, index: AtomicInteger): Int {
            if (isNotSamePlayers(currenPlayer, previousPlayer) && playersScoreAreDifferent(currenPlayer, previousPlayer)) {
                return index.get()
            }
            return index.incrementAndGet()
        }

        private fun playersScoreAreDifferent(
            currenPlayer: Player,
            previousPlayer: Player
        ): Boolean = currenPlayer.score?.points() == previousPlayer.score?.points()

        private fun isNotSamePlayers(
            currenPlayer: Player,
            previousPlayer: Player
        ): Boolean = currenPlayer != previousPlayer
    }

    fun getPlayersSortedByRanking(): List<PlayerWithRanking> {
        return players.values.toList()
    }

    fun getPlayer(id: Int): PlayerWithRanking {

        return players[id.toInt()] ?: throw PlayerNotFoundException("Player not found with id $id")
    }

    fun getNewPlayerToAdd(playerToAdd: Player): Player {

        if (playerNameAlreadyExists(playerToAdd)) {
            throw PlayerAlreadyExistsException("Player already exists with name : " + playerToAdd.name)
        }

        // TODO 6 : other tournament checks may be carried out before adding a new player, limit on the number of players for example

        return playerToAdd
    }

    private fun playerNameAlreadyExists(playerToAdd: Player): Boolean =
        players.any { it.value.player.name == playerToAdd.name }

}
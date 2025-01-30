package fr.betclic.tournament.api.mapper

import fr.betclic.tournament.api.view.PlayerView
import fr.betclic.tournament.core.domain.Player
import fr.betclic.tournament.core.domain.PlayerWithRanking

class PlayerViewMapper private constructor (private val playerView: PlayerView) {

    companion object {
        fun from(playerWithRanking: PlayerWithRanking): PlayerViewMapper {

            return PlayerViewMapper(
                PlayerView(
                    playerWithRanking.player.id,
                    playerWithRanking.player.name,
                    playerWithRanking.player.score?.points(),
                    playerWithRanking.ranking
                )
            )
        }

        fun from(player: Player): PlayerViewMapper {

            return PlayerViewMapper(
                PlayerView(
                    player.id,
                    player.name,
                    player.score?.points()
                )
            )
        }
    }


    fun get(): PlayerView {
        return playerView
    }
}
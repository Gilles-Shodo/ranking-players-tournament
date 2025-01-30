package fr.betclic.tournament.api.mapper

import fr.betclic.tournament.api.request.AddPlayerRequest
import fr.betclic.tournament.api.request.ChangePlayerPointsRequest
import fr.betclic.tournament.core.domain.Player
import fr.betclic.tournament.core.domain.Score

class PlayerMapper private constructor (private val playerToAdd: Player) {

    companion object {

        fun from(playerToAdd: AddPlayerRequest): PlayerMapper {

            return PlayerMapper(
                Player(
                    null,
                    playerToAdd.name
                )
            )
        }

        fun from(playerToUpdate: ChangePlayerPointsRequest, id: Int): PlayerMapper {

            return PlayerMapper(
                Player(
                    id,
                    null,
                    Score.Companion.from(playerToUpdate.points)
                )
            )
        }
    }

    fun get(): Player {
        return playerToAdd
    }
}
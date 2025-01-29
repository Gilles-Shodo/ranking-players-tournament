package fr.betclic.tournament.core.domain

class PlayerToUpdate private constructor (private val playerToUpdate: Player) {

    companion object {

        fun from(playerToUpdate: Player): PlayerToUpdate {

            return PlayerToUpdate(
                playerToUpdate
            )
        }
    }

    fun mergeWith(playerWithChange: Player): Player {
        return Player(
            playerToUpdate.id,
            playerWithChange.name ?: playerToUpdate.name,
            playerWithChange.score ?: playerToUpdate.score
        )
    }
}
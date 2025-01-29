package fr.betclic.tournament.core.domain

import fr.betclic.tournament.core.exception.PlayerScoreExceededException

class Score private constructor(private val points : Int) {

    companion object {
        fun from(points: Int): Score {

            val threshold = 1000 // TODO 2 : place value into config file
            if (points > threshold) {
                throw PlayerScoreExceededException("Player points cannot exceeds $threshold")
            }

            return Score(points)
        }
    }

    fun points(): Int {
        return  points
    }

}
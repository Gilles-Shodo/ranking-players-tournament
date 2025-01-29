package fr.betclic.tournament.core.domain

import fr.betclic.tournament.core.exception.PlayerScoreExceededException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import kotlin.test.Test

class ScoreTest {

    @Test
    fun score_should_return_points_when_not_exceeds_limit() {
        val score = Score.from(100)

        assertThat(score.points()).isEqualTo(100)
    }

    @Test
    fun score_should_not_exceeds_limit() {

        assertThatThrownBy { Score.from(1001) }
            .isInstanceOf(PlayerScoreExceededException::class.java)
            .hasMessage("Player points cannot exceeds 1000")
    }
}
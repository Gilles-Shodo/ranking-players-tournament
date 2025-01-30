package fr.betclic.tournament.api.mapper

import fr.betclic.tournament.core.domain.Player
import fr.betclic.tournament.core.domain.PlayerWithRanking
import fr.betclic.tournament.core.domain.Score
import org.assertj.core.api.Assertions
import org.junit.Test

class PlayerViewMapperTest {

    @Test
    fun should_map_player_view_from_player_with_ranking() {

        // Given
        val playerViewMapper: PlayerViewMapper =  PlayerViewMapper.Companion.from(
            PlayerWithRanking(
                Player(
                    1,
                    "Player 1",
                    Score.Companion.from(100)
                ), 1
            )
        )

        // When
        val playerView = playerViewMapper.get()

        // Then
        Assertions.assertThat(playerView.id).isEqualTo(1)
        Assertions.assertThat(playerView.name).isEqualTo("Player 1")
        Assertions.assertThat(playerView.points).isEqualTo(100)
        Assertions.assertThat(playerView.ranking).isEqualTo(1)
    }

    @Test
    fun should_map_player_view_from_player() {

        // Given
        val playerViewMapper: PlayerViewMapper =  PlayerViewMapper.Companion.from(
            Player(
                1,
                "Player 1",
                Score.Companion.from(100)
            )
        )

        // When
        val playerView = playerViewMapper.get()

        // Then
        Assertions.assertThat(playerView.id).isEqualTo(1)
        Assertions.assertThat(playerView.name).isEqualTo("Player 1")
        Assertions.assertThat(playerView.points).isEqualTo(100)
        Assertions.assertThat(playerView.ranking).isNull()
    }
}
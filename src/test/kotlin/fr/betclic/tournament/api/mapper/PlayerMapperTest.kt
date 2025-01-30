package fr.betclic.tournament.api.mapper

import fr.betclic.tournament.api.request.AddPlayerRequest
import fr.betclic.tournament.api.request.ChangePlayerPointsRequest
import org.assertj.core.api.Assertions.*
import org.junit.Test

class PlayerMapperTest {

    @Test
    fun should_map_player_from_add_player_request() {

        // Given
        val playerMapper: PlayerMapper =  PlayerMapper.from(AddPlayerRequest("Player 1"))

        // When
        val player = playerMapper.get()

        // Then
        assertThat(player.id).isNull()
        assertThat(player.name).isEqualTo("Player 1")
        assertThat(player.score).isNull()
    }

    @Test
    fun should_map_player_from_change_player_points_request() {

        // Given
        val playerMapper: PlayerMapper =  PlayerMapper.from(ChangePlayerPointsRequest(100), 1)

        // When
        val player = playerMapper.get()

        // Then
        assertThat(player.id).isEqualTo(1)
        assertThat(player.name).isNull()
        assertThat(player.score?.points()).isEqualTo(100)
    }

}
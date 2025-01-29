package fr.betclic.tournament.core.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class PlayerToUpdateTest {

    @Test
    fun should_update_player_data_when_present() {

        // Given
        val playerMapper: PlayerToUpdate = PlayerToUpdate.from(Player(1, "Player 1"))

        // When
        val playerWithUpdate = playerMapper.mergeWith(Player(1, null, Score.from(100)))

        // Then
        assertThat(playerWithUpdate.id).isEqualTo(1)
        assertThat(playerWithUpdate.name).isEqualTo("Player 1")
        assertThat(playerWithUpdate.score?.points()).isEqualTo(100)
    }

    @Test
    fun should_return_original_customer_when_not_player_changes_are_present() {

        // Given
        val playerMapper: PlayerToUpdate = PlayerToUpdate.from(Player(1, "Player 1", Score.from(100)))

        // When
        val playerWithUpdate = playerMapper.mergeWith(Player(null, null, null))

        // Then
        assertThat(playerWithUpdate.id).isEqualTo(1)
        assertThat(playerWithUpdate.name).isEqualTo("Player 1")
        assertThat(playerWithUpdate.score?.points()).isEqualTo(100)
    }

}
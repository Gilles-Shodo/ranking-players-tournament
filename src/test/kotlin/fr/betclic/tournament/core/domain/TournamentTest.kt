package fr.betclic.tournament.core.domain

import fr.betclic.tournament.core.exception.PlayerAlreadyExistsException
import fr.betclic.tournament.core.exception.PlayerNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import kotlin.test.Test

class TournamentTest {

    @Test
    fun should_not_return_players_when_tournament_is_empty() {

        // Given
        val tournament = Tournament.withPlayers(emptyList())

        // When
        val players: List<PlayerWithRanking> = tournament.getPlayersSortedByRanking()

        // Then
        assertThat(players).isEmpty()
    }

    @Test
    fun when_only_one_player_is_present_in_tournament_he_should_be_first_in_ranking() {

        // Given
        val tournament = Tournament.withPlayers(listOf(Player(1, "Player 1", Score.from(100))))

        // When
        val players: List<PlayerWithRanking> = tournament.getPlayersSortedByRanking()

        // Then
        assertThat(players).hasSize(1)
        assertThat(players[0].player.id).isEqualTo(1)
        assertThat(players[0].player.name).isEqualTo("Player 1")
        assertThat(players[0].player.score?.points()).isEqualTo(100)
        assertThat(players[0].ranking).isEqualTo(1)
    }

    @Test
    fun when_two_players_are_present_in_tournament_they_should_be_sort_by_best_score() {

        // Given
        val tournament = Tournament.withPlayers(listOf(Player(1, "Player 1", Score.from(100)), Player(2, "Player 2", Score.from(50))))

        // When
        val players: List<PlayerWithRanking> = tournament.getPlayersSortedByRanking()

        // Then
        assertThat(players).hasSize(2)
        assertThat(players[0].player.id).isEqualTo(1)
        assertThat(players[0].player.name).isEqualTo("Player 1")
        assertThat(players[0].player.score?.points()).isEqualTo(100)
        assertThat(players[0].ranking).isEqualTo(1)

        assertThat(players[1].player.id).isEqualTo(2)
        assertThat(players[1].player.name).isEqualTo("Player 2")
        assertThat(players[1].player.score?.points()).isEqualTo(50)
        assertThat(players[1].ranking).isEqualTo(2)
    }

    @Test
    fun when_two_players_have_same_score_they_should_have_the_same_ranking() {

        // Given
        val tournament = Tournament.withPlayers(listOf(Player(1, "Player 1", Score.from(100)), Player(2, "Player 2", Score.from(100))))

        // When
        val players: List<PlayerWithRanking> = tournament.getPlayersSortedByRanking()

        // Then
        assertThat(players).hasSize(2)
        assertThat(players[0].player.id).isEqualTo(1)
        assertThat(players[0].player.name).isEqualTo("Player 1")
        assertThat(players[0].player.score?.points()).isEqualTo(100)
        assertThat(players[0].ranking).isEqualTo(1)

        assertThat(players[1].player.id).isEqualTo(2)
        assertThat(players[1].player.name).isEqualTo("Player 2")
        assertThat(players[1].player.score?.points()).isEqualTo(100)
        assertThat(players[1].ranking).isEqualTo(1)
    }

    @Test
    fun when_players_are_not_ordered_by_score_they_should_be_sort_by_best_score() {

        // Given
        val tournament = Tournament.withPlayers(
            listOf(
                Player(1, "Player 1", Score.from(50)),
                Player(2, "Player 2", Score.from(100)),
                Player(3, "Player 3", Score.from(10)),
                Player(4, "Player 4", Score.from(50))
            )
        )

        // When
        val players: List<PlayerWithRanking> = tournament.getPlayersSortedByRanking()

        // Then
        assertThat(players).hasSize(4)
        assertThat(players[0].player.id).isEqualTo(2)
        assertThat(players[0].player.name).isEqualTo("Player 2")
        assertThat(players[0].player.score?.points()).isEqualTo(100)
        assertThat(players[0].ranking).isEqualTo(1)

        assertThat(players[1].player.id).isEqualTo(1)
        assertThat(players[1].player.name).isEqualTo("Player 1")
        assertThat(players[1].player.score?.points()).isEqualTo(50)
        assertThat(players[1].ranking).isEqualTo(2)

        assertThat(players[2].player.id).isEqualTo(4)
        assertThat(players[2].player.name).isEqualTo("Player 4")
        assertThat(players[2].player.score?.points()).isEqualTo(50)
        assertThat(players[2].ranking).isEqualTo(2)

        assertThat(players[3].player.id).isEqualTo(3)
        assertThat(players[3].player.name).isEqualTo("Player 3")
        assertThat(players[3].player.score?.points()).isEqualTo(10)
        assertThat(players[3].ranking).isEqualTo(3)
    }

    @Test
    fun should_retrieve_player_with_ranking_when_requested() {

        // Given
        val tournament = Tournament.withPlayers(
            listOf(
                Player(1, "Player 1", Score.from(50)),
                Player(2, "Player 2", Score.from(100)),
                Player(3, "Player 3", Score.from(10)),
                Player(4, "Player 4", Score.from(50))
            )
        )

        // When
        val player: PlayerWithRanking = tournament.getPlayer(3)

        // Then
        assertThat(player.player.id).isEqualTo(3)
        assertThat(player.player.name).isEqualTo("Player 3")
        assertThat(player.player.score?.points()).isEqualTo(10)
        assertThat(player.ranking).isEqualTo(3)
    }

    @Test
    fun should_throw_player_not_found_exception_when_player_id_is_not_found() {

        // Given
        val tournament = Tournament.withPlayers(
            listOf(
                Player(1, "Player 1", Score.from(50)),
                Player(2, "Player 2", Score.from(100)),
                Player(3, "Player 3", Score.from(10)),
                Player(4, "Player 4", Score.from(50))
            )
        )

        // When
        assertThatThrownBy { tournament.getPlayer(5) }
            .isInstanceOf(PlayerNotFoundException::class.java)
            .hasMessage("Player not found with id 5")
    }

    @Test
    fun should_get_new_player_to_add_to_tournament() {

        // Given
        val tournament = Tournament.withPlayers(listOf(Player(1, "Player 1", Score.from(100))))

        // When
        val player: Player = tournament.getNewPlayerToAdd(Player(null, "Player 2"))

        // Then
        assertThat(player.id).isNull()
        assertThat(player.name).isEqualTo("Player 2")
        assertThat(player.score).isNull()
    }

    @Test
    fun should_throw_player_already_exists_exception_when_other_player_has_same_name() {

        // Given
        val tournament = Tournament.withPlayers(listOf(Player(1, "Player 1", Score.from(100))))

        // When Then
        assertThatThrownBy { tournament.getNewPlayerToAdd(Player(null, "Player 1")) }
            .isInstanceOf(PlayerAlreadyExistsException::class.java)
            .hasMessage("Player already exists with name : Player 1")
    }

}
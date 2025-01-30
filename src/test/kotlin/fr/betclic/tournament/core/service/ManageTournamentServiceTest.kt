package fr.betclic.tournament.core.service

import fr.betclic.tournament.core.domain.Player
import fr.betclic.tournament.core.domain.Score
import fr.betclic.tournament.core.outbound.ManageTournamentRepository
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.BeforeTest
import kotlin.test.Test

class ManageTournamentServiceTest {

    private lateinit var manageTournamentRepository: ManageTournamentRepository

    private lateinit var manageTournamentService: ManageTournamentService

    @BeforeTest
    fun setUp() {
        manageTournamentRepository = mockk<ManageTournamentRepository>()
        manageTournamentService = ManageTournamentService(manageTournamentRepository)
    }

    @Test
    fun should_add_one_new_player() {

        // Given
        val playerRetrieved = Player(1, "Player 1", Score.from(100))
        every { manageTournamentRepository.getAllPlayers() } returns listOf(playerRetrieved)

        val playerAdded = Player(1, "Player 2")
        every { manageTournamentRepository.addNewPlayer(any()) } returns playerAdded
        val playerToAdd = Player(null, "Player 2")

        // When
        val player = manageTournamentService.addNewPlayer(playerToAdd)

        // Then
        verify { manageTournamentRepository.getAllPlayers() }
        verify { manageTournamentRepository.addNewPlayer(playerToAdd) }
        assertThat(player.name).isEqualTo("Player 2")
        assertThat(player.score).isNull()
    }

    @Test
    fun should_update_player_points_number() {

        // Given
        val playerRetrieved = Player(1, "Player 1")
        every { manageTournamentRepository.getPlayerById(any()) } returns playerRetrieved

        val playerUpdated = Player(1, "Player 1", Score.from(100))
        val playerSlot = slot<Player>()
        every { manageTournamentRepository.updatePlayer(capture(playerSlot)) } returns playerUpdated

        val playerToUpdate = Player(1, null, Score.from(100))

        // When
        val player = manageTournamentService.updatePlayer(playerToUpdate)

        // Then
        verify { manageTournamentRepository.updatePlayer(playerSlot.captured) }
        assertThat(playerSlot.captured.id).isEqualTo(1)
        assertThat(playerSlot.captured.name).isEqualTo("Player 1")
        assertThat(playerSlot.captured.score?.points()).isEqualTo(100)

        assertThat(player.name).isEqualTo("Player 1")
        assertThat(player.score?.points()).isEqualTo(100)
    }

    @Test
    fun should_return_one_player_by_id() {

        // Given
        val playerRetrieved = Player(1, "Player 1", Score.from(100))
        every { manageTournamentRepository.getAllPlayers() } returns listOf(playerRetrieved)

        // When
        val player = manageTournamentService.getPlayerById(1)

        // Then
        verify { manageTournamentRepository.getAllPlayers() }
        assertThat(player.player.name).isEqualTo("Player 1")
        assertThat(player.player.score?.points()).isEqualTo(100)
        assertThat(player.ranking).isEqualTo(1)
    }

    @Test
    fun should_return_all_tournament_players() {

        // Given
        val playersRetrieved = listOf(
            Player(1, "Player 1", Score.from(100)),
            Player(2, "Player 2", Score.from(50))
        )
        every { manageTournamentRepository.getAllPlayers() } returns playersRetrieved

        // When
        val players = manageTournamentService.getAllPlayers()

        // Then
        assertThat(players).hasSize(2);
        assertThat(players.get(0).player.name).isEqualTo("Player 1")
        assertThat(players.get(0).player.score?.points()).isEqualTo(100)
        assertThat(players.get(0).ranking).isEqualTo(1)
        assertThat(players.get(1).player.name).isEqualTo("Player 2")
        assertThat(players.get(1).player.score?.points()).isEqualTo(50)
        assertThat(players.get(1).ranking).isEqualTo(2)
    }

    @Test
    fun should_delete_all_players() {

        // Given
        every { manageTournamentRepository.removeAllPlayers() } just return

        // When
        manageTournamentService.removeAllPlayers()

        // Then
        verify { manageTournamentRepository.removeAllPlayers() }
    }
}
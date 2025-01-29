package fr.betclic.tournament.database.repository

import fr.betclic.tournament.api.config.InMemoryDatabase
import fr.betclic.tournament.core.domain.Player
import fr.betclic.tournament.core.domain.Score
import fr.betclic.tournament.core.exception.PlayerNotCreatedException
import fr.betclic.tournament.core.exception.PlayerNotFoundException
import fr.betclic.tournament.core.exception.PlayerNotUpdatedException
import fr.betclic.tournament.core.exception.PlayersNotRemovedException
import fr.betclic.tournament.core.outbound.ManageTournamentRepository
import java.sql.ResultSet
import java.sql.Statement

class ManageTournamentInMemoryRepository(private val inMemoryDatabase: InMemoryDatabase?) : ManageTournamentRepository {

    override fun addNewPlayer(playerToAdd: Player): Player {

        val statement: Statement = inMemoryDatabase?.createDataSource()!!.createStatement()
        statement.execute("INSERT INTO tournament (name) VALUES ('${playerToAdd.name}')")

        val resultSet = inMemoryDatabase?.createDataSource()!!.createStatement().executeQuery("SELECT * FROM tournament ORDER BY id DESC LIMIT 1")

        if (resultSet.next()) {

            var playerCreated = mapPlayerFromResultSet(resultSet)

            if (playerToAdd.name == playerCreated.name) {
                return  playerCreated
            }

        }

        throw PlayerNotCreatedException("Player not created : $playerToAdd")
    }

    override fun updatePlayer(playerToUpdate: Player): Player {

        val statement: Statement = inMemoryDatabase?.createDataSource()!!.createStatement()
        statement.execute("UPDATE tournament SET name = '${playerToUpdate.name}', points = ${playerToUpdate.score?.points()} WHERE id = ${playerToUpdate.id}")

        val resultSet = inMemoryDatabase?.createDataSource()!!.createStatement().executeQuery("SELECT * FROM tournament where id = '${playerToUpdate.id}'")


        if (resultSet.next()) {

            var playerUpdated = mapPlayerFromResultSet(resultSet)

            if (playerToUpdate.id == playerUpdated.id) {
                return  playerUpdated
            }

        }

        throw PlayerNotUpdatedException("Player not created : ${playerToUpdate.id}")
    }

    override fun getPlayerById(id: Int): Player {

        val resultSet = inMemoryDatabase?.createDataSource()!!.createStatement().executeQuery("SELECT * FROM tournament where id like '${id}'")

        if (resultSet.next()) {
            return mapPlayerFromResultSet(resultSet)

        }

        throw PlayerNotFoundException("Player not found : $id")
    }

    override fun getAllPlayers(): List<Player> {

        val resultSet = inMemoryDatabase?.createDataSource()!!.createStatement().executeQuery("SELECT * FROM tournament")

        var players = mutableListOf<Player>()

        while (resultSet.next()) {

            val player = mapPlayerFromResultSet(resultSet)

            players.add(player)

        }

        return players
    }

    override fun removeAllPlayers() {

        inMemoryDatabase?.createDataSource()!!.createStatement().execute("delete FROM tournament")

        val resultSet = inMemoryDatabase?.createDataSource()!!.createStatement().executeQuery("SELECT * FROM tournament")

        if (resultSet.next()) {
            throw PlayersNotRemovedException("Player not removed")
        }

    }

    private fun mapPlayerFromResultSet(resultSet: ResultSet): Player {
        val id = resultSet.getInt("id")
        val name = resultSet.getString("name")
        val points = resultSet.getInt("points")

        return Player(id, name, Score.from(points))
    }

}
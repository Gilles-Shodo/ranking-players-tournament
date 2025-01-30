package fr.betclic.tournament.api.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.sql.Connection
import java.sql.DriverManager
import java.sql.Statement

private const val INIT_DATABASE =
    "CREATE TABLE IF NOT EXISTS tournament (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), points INT, ranking INT)"

class InMemoryDatabase() {

    private var connection: Connection? = null

    fun createDataSource(): Connection? {
        val config = HikariConfig()

        // TODO 2 : extract data into config file
        config.jdbcUrl = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"
        config.username = "tournament"
        config.password = "password"
        config.driverClassName = "org.h2.Driver"

        connection = HikariDataSource(config).connection

        val url = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;"
        try {
            connection = DriverManager.getConnection(url, "tournament", "password")
            val statement: Statement = connection!!.createStatement()
            statement.execute(INIT_DATABASE)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }

        return connection

    }
}
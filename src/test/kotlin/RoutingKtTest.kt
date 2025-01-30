import fr.betclic.tournament.api.module
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.sql.Connection
import io.ktor.server.testing.*
import org.assertj.core.api.Assertions.assertThat
import kotlin.test.Test
import java.sql.DriverManager
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

private const val TABLE_CREATION =
    "CREATE TABLE IF NOT EXISTS tournament (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255), points INT)"

private const val INSERT_PLAYER_ONE = "INSERT INTO tournament (name, points) VALUES ('Player 1', 100)"

private const val REMOVE_PLAYER_TWO = "INSERT INTO tournament (name, points) VALUES ('Player 2', 50)"

private const val DROP_DATABASE = "DROP TABLE IF EXISTS tournament"

class RoutingKtTest {

    private lateinit var connection: Connection

    @BeforeTest
    fun initDataInDatabase() {
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "tournament", "password")
        connection.createStatement().execute(TABLE_CREATION)
        connection.createStatement().execute(INSERT_PLAYER_ONE)
        connection.createStatement().execute(REMOVE_PLAYER_TWO)
    }

    @AfterTest
    fun removeDatabase() {
        connection.createStatement().execute(DROP_DATABASE)
        connection.close()
    }

    @Test
    fun should_add_one_new_player() = testApplication {

        application {
            module()
        }

        val response = client.post("/tournament/players") {

            contentType(ContentType.Application.Json)
            header("Content-Type", "application/json")
            setBody("{\"name\":\"Player 3\"}")
        }

        assertThat(response.status).isEqualTo(HttpStatusCode.Created)
        assertThat(response.bodyAsText()).isEqualTo("{\"id\":3,\"name\":\"Player 3\",\"points\":0}")
    }

    @Test
    fun should_update_player_points_number() = testApplication {

        application {
            module()
        }

        val response = client.patch("/tournament/players/1") {

            contentType(ContentType.Application.Json)
            header("Content-Type", "application/json")
            setBody("{\"points\":100}")
        }

        assertThat(response.status).isEqualTo(HttpStatusCode.OK)
        assertThat(response.bodyAsText()).isEqualTo("{\"id\":1,\"name\":\"Player 1\",\"points\":100}")
    }

    @Test
    fun should_return_one_player_by_id() = testApplication {

        application {
            module()
        }

        client.get("/tournament/players/1").apply {

            assertThat(status).isEqualTo(HttpStatusCode.OK)
            assertThat(bodyAsText()).isEqualTo("{\"id\":1,\"name\":\"Player 1\",\"points\":100,\"ranking\":1}")
        }
    }

    @Test
    fun should_return_all_tournament_players() = testApplication {

        application {
            module()
        }

        client.get("/tournament/players").apply {

            assertThat(status).isEqualTo(HttpStatusCode.OK)
            assertThat(bodyAsText()).isEqualTo("[{\"id\":1,\"name\":\"Player 1\",\"points\":100,\"ranking\":1},{\"id\":2,\"name\":\"Player 2\",\"points\":50,\"ranking\":2}]")
        }
    }

    @Test
    fun should_delete_all_players() = testApplication {

        application {
            module()
        }

        client.delete("/tournament/players").apply {

            assertThat(status).isEqualTo(HttpStatusCode.NoContent)
            assertThat(bodyAsText()).isEmpty()
        }

        client.get("/tournament/players").apply {

            assertThat(status).isEqualTo(HttpStatusCode.OK)
            assertThat(bodyAsText()).isEqualTo("[]")
        }
    }

}
# Ranking of players in a tournament - REST Api 

REST api to manage players ranking in tournament

Resources list (Postman collection) => ranking_players_tournament_collection.json (into project resources folder)

Project uses : 
- Ktor v3 (for server api)
- Koin (for dependencies injection)
- H2 In memory database

Project carried out in hexagonal architecture with DDD tactic pattern (for business management rules)

## Building & Running

To build or run the project, use the following tasks:

| Task                          | Description      |
|-------------------------------|------------------|
| `gradle test`                 | Run the tests    |
| `gradle clean build`          | Clean and Build  |
| `run`                         | Run the server   |

For standalone usage, use the following tasks:

| Task                                           | Description                                                          |
|------------------------------------------------|----------------------------------------------------------------------|
| `gradle buildFatJar`                           | Build an executable JAR of the server with all dependencies included |
| `java -jar ranking-players-tournament-all.jar` | Run jar (location : ranking-players-tournament/build/libs)           |

Server is accessible from url : http://localhost:8080/

## Todo list
- Add Swagger documentation (TODO 1)
- Extract database and config data into config files (TODO 2)
- Harmonize gradle version dependencies management (TODO 3)
- Add Dynamo DB implementation (TODO 4)
- Add unit tests for api exception handler (TODO 5)
- Add others tournament rules like limit of the number of players for example (TODO 6)
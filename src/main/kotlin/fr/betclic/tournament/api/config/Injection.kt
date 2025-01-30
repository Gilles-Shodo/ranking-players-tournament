package fr.betclic.tournament.api.config

import fr.betclic.tournament.core.inbound.ManageTournament
import fr.betclic.tournament.core.outbound.ManageTournamentRepository
import fr.betclic.tournament.core.service.ManageTournamentService
import fr.betclic.tournament.database.repository.ManageTournamentInMemoryRepository
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureInjection() {
    install(Koin) {
        modules(injectionModule)
    }
}

val injectionModule = module {
    single<ManageTournament> { ManageTournamentService(ManageTournamentInMemoryRepository(InMemoryDatabase())) }
    single<ManageTournamentRepository> { ManageTournamentInMemoryRepository(InMemoryDatabase()) }
}
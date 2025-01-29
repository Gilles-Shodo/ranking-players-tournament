package fr.betclic.tournament.core.domain

data class PlayerWithRanking  (
    val player: Player,
    val ranking: Int? = 0
)
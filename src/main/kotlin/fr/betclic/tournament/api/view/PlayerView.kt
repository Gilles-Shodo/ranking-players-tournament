package fr.betclic.tournament.api.view

import kotlinx.serialization.Serializable

@Serializable
data class PlayerView (
    val id: Int? = null,
    val name: String? = null,
    val points: Int? = null,
    val ranking: Int? = null
)
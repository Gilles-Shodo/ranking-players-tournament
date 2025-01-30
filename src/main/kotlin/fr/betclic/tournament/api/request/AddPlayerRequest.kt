package fr.betclic.tournament.api.request

import kotlinx.serialization.Serializable

@Serializable
data class AddPlayerRequest (
    val name: String
)
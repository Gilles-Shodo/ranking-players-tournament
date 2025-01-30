package fr.betclic.tournament.api.request

import kotlinx.serialization.Serializable

@Serializable
data class ChangePlayerPointsRequest (
    val points: Int
)
package xyz.bolhy91.example.ktor.sql.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Patch(
    @SerialName("small")
    val small: String?,
    @SerialName("large")
    val large: String?
)

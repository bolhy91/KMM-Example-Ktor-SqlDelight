package xyz.bolhy91.example.ktor.sql.entities

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Links(
    @SerialName("patch")
    val patch: Patch?,
    @SerialName("article")
    val article: String?
)

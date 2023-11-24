package xyz.bolhy91.example.ktor.sql.entities

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RocketLaunch(
    @SerialName("flight_number")
    val flightNumber: Int,
    @SerialName("name")
    val missionName: String,
    @SerialName("date_utc")
    val launchDateUTC: String,
    @SerialName("details")
    val details: String?,
    @SerialName("success")
    val launchSuccess: Boolean?,
    @SerialName("links")
    val links: Links
) {
    var launchYear = launchDateUTC.toInstant().toLocalDateTime(TimeZone.UTC).year
}

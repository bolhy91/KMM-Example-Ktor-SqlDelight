package xyz.bolhy91.example.ktor.sql

import xyz.bolhy91.example.ktor.sql.cache.Database
import xyz.bolhy91.example.ktor.sql.cache.DatabaseDriverFactory
import xyz.bolhy91.example.ktor.sql.entities.RocketLaunch
import xyz.bolhy91.example.ktor.sql.network.SpaceXApi

class SpaceXSDK(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = Database(databaseDriverFactory)
    private val api = SpaceXApi()

    @Throws(Exception::class)
    suspend fun getAllLaunches(forceReload: Boolean): List<RocketLaunch> {
        val cachedLaunches = database.getAllLaunches()
        return if (cachedLaunches.isNotEmpty() && !forceReload) {
            cachedLaunches
        } else {
            api.getAllLaunches().also { launches ->
                database.clearDatabase()
                database.createLaunches(launches)
            }
        }
    }
}
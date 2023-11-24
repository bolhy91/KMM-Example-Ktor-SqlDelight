package xyz.bolhy91.example.ktor.sql

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
package duck.hansson.odd.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

package duck.hansson.odd

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
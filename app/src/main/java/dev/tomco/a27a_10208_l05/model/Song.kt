package dev.tomco.a27a_10208_l05.model

data class Song private constructor(
    val name: String,
    val artist: String,
    val dusrationInSeconds: Int,
    val releaseDate: Long,
    val views: Long,
    val tags: List<String>
) {
    class Builder {
        private var name: String = ""
        private var artist: String = ""
        private var dusrationInSeconds: Int = 0
        private var releaseDate: Long = 0L
        private var views: Long = 0L
        private var tags: List<String> = emptyList()

        fun name(name: String) = apply { this.name = name }
        fun artist(artist: String) = apply { this.artist = artist }
        fun dusrationInSeconds(dusrationInSeconds: Int) =
            apply { this.dusrationInSeconds = dusrationInSeconds }

        fun releaseDate(releaseDate: Long) = apply { this.releaseDate = releaseDate }
        fun views(views: Long) = apply { this.views = views }
        fun tags(tags: List<String>) = apply { this.tags = tags.toList() }

        fun build() = Song(
            releaseDate = releaseDate,
            name = name,
            dusrationInSeconds = dusrationInSeconds,
            artist = artist,
            views = views,
            tags = tags
        )
    }
}
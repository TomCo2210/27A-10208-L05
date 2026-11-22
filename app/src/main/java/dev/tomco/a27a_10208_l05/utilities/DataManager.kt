package dev.tomco.a27a_10208_l05.utilities

import dev.tomco.a27a_10208_l05.model.Playlist
import dev.tomco.a27a_10208_l05.model.Song
import java.time.LocalDate

object DataManager {
    fun generatePlaylist(): Playlist {
        return Playlist
            .Builder()
            .name("Top Three!")
            .addSong(
                Song.Builder()
                    .name("Gangnam Style")
                    .artist("PSY")
                    .dusrationInSeconds(252)
                    .releaseDate(
                        LocalDate.of(2012,7,15).toEpochDay()
                    )
                    .tags(listOf("Korean", "K-pop"))
                    .views(6_138_152_201)
                    .build()
            )
            .addSong(
                Song.Builder()
                    .name("Hello")
                    .artist("Adele")
                    .dusrationInSeconds(372)
                    .releaseDate(LocalDate.of(2015,10,23).toEpochDay())
                    .tags(listOf("British", "pop"))
                    .views(3_289_520_590)
                    .build()
            )
            .addSong(
                Song.Builder()
                    .name("חיים שלי")
                    .artist("זהבה בן")
                    .dusrationInSeconds(242)
                    .releaseDate(LocalDate.of(2011,11,22).toEpochDay())
                    .tags(listOf("ישראלי", "מזרחית" ))
                    .views(1_558_591)
                    .build()
            )
            .build()
    }
}
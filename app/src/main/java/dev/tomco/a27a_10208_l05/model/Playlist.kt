package dev.tomco.a27a_10208_l05.model

data class Playlist private constructor(
    val name: String,
    val allSong: List<Song>
){
    class Builder{
        private var name: String = ""
        private var allSong: MutableList<Song> = mutableListOf()

        fun name(name: String) = apply { this.name = name}
        fun addSong(song: Song) = apply { allSong.add(song) }

        fun build() = Playlist(name, allSong.toList())
    }
}

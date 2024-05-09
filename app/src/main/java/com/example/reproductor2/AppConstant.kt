package com.example.reproductor2

data class Song (
    val tittle: String,
    val audioResId: Int,
    val imageResId:Int
)


class AppConstant {
    companion object{

        const val LOG_MAIN_ACTIVITY = "MainActivityReproducto2"
        const val MEDIA_PLAYER_POSITION = "POSITION"
        const val IS_PLAYING = "IS_PLAYING"
        const val CURRENT_SONG_INDEX = "CURRENT_SONG_INDEX"


        val songs = listOf(
            Song("Pretty Please Remix - Dua Lipa",R.raw.pp_remix,R.drawable.pretty_please),
            Song("Summertime Sadness Remix - Lana del Rey", R.raw.lr_ss, R.drawable.lr_ss),
            Song("In the end Remix - Linkin Park", R.raw.lp_in_the_end_remix,
                R.drawable.lp_in_the_emd_remix)
        )

    }
}
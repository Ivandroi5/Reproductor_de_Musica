package com.example.reproductor2

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.reproductor2.AppConstant.Companion.LOG_MAIN_ACTIVITY
import com.example.reproductor2.AppConstant.Companion.MEDIA_PLAYER_POSITION
import com.example.reproductor2.AppConstant.Companion.CURRENT_SONG_INDEX
import com.example.reproductor2.AppConstant.Companion.IS_PLAYING
import com.example.reproductor2.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var binding: ActivityMainBinding
    private var position: Int = 0
    private var currentSongIndex: Int = 0
    private var isPlaying: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savedInstanceState?.let {
            position = it.getInt(MEDIA_PLAYER_POSITION)
            isPlaying = it.getBoolean(IS_PLAYING)
            currentSongIndex = it.getInt(CURRENT_SONG_INDEX)
        }

        binding.playPauseButton.setOnClickListener { togglePlayPause() }
        binding.playNextButton.setOnClickListener { playNextSong() }
        binding.playPreviousButton.setOnClickListener { playPrevSong() }

    }

    override fun onStart() {
        super.onStart()
        initializeMediaPlayer()
        Log.i(LOG_MAIN_ACTIVITY, "onStart")

    }

    override fun onResume() {
        super.onResume()
        Log.i(LOG_MAIN_ACTIVITY, "onResume()")
        mediaPlayer?.seekTo(position)
        if (isPlaying) mediaPlayer?.start()

    }

    override fun onPause() {
        super.onPause()
        Log.i(LOG_MAIN_ACTIVITY, "onPause")
        mediaPlayer?.pause()
        position = mediaPlayer?.currentPosition ?: 0
        isPlaying = false
    }

    override fun onStop() {
        super.onStop()
        Log.i(LOG_MAIN_ACTIVITY, "onStop")
        releaseMediaPlayer()
        isPlaying = false
    }
    override fun onRestart() {
        super.onRestart()
        Log.i(LOG_MAIN_ACTIVITY, "onRestart")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(LOG_MAIN_ACTIVITY, "onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(MEDIA_PLAYER_POSITION, position)
        outState.putBoolean(IS_PLAYING, isPlaying)
        outState.putInt(CURRENT_SONG_INDEX, currentSongIndex)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentSongIndex = savedInstanceState.getInt(CURRENT_SONG_INDEX)
        val currentSong = AppConstant.songs[currentSongIndex]
        updateSongInfo(currentSong)
    }

    private fun initializeMediaPlayer() {
        if (mediaPlayer == null) {
            val currentSong = AppConstant.songs[currentSongIndex]
            mediaPlayer = MediaPlayer.create(this, currentSong.audioResId)
            mediaPlayer?.setOnCompletionListener {
                playNextSong()
            }
            updateSongInfo(currentSong)
            updatePlayPauseButton()
        }
    }

    private fun releaseMediaPlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun togglePlayPause() {
        if (isPlaying) {
            mediaPlayer?.pause()
        } else {
            mediaPlayer?.start()
        }
        isPlaying = !isPlaying
        updatePlayPauseButton()
    }

    private fun playNextSong() {
        currentSongIndex = (currentSongIndex + 1) % AppConstant.songs.size
        val currentSong = AppConstant.songs[currentSongIndex]
        releaseMediaPlayer()
        mediaPlayer = MediaPlayer.create(this, currentSong.audioResId)
        updateSongInfo(currentSong)
        updatePlayPauseButton()
        if (isPlaying) {
            mediaPlayer?.start()
        }
    }

    private fun playPrevSong() {
        currentSongIndex =
            if (currentSongIndex == 0) AppConstant.songs.size - 1 else currentSongIndex - 1
        val currentSong = AppConstant.songs[currentSongIndex]
        releaseMediaPlayer()
        mediaPlayer = MediaPlayer.create(this, currentSong.audioResId)
        updateSongInfo(currentSong)
        updatePlayPauseButton()
        if (isPlaying) {
            mediaPlayer?.start()
        }
    }

    private fun updatePlayPauseButton() {
        binding.playPauseButton.text = if (isPlaying) "Pause" else "Play"
    }

    private fun updateSongInfo(song: Song) {

        binding.titleTextView.text = song.tittle
        binding.albumCoverImageView.setImageResource(song.imageResId)
    }
}


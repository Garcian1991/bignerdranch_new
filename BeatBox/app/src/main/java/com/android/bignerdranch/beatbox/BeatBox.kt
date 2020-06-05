package com.android.bignerdranch.beatbox

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import java.io.IOException
import java.lang.Exception

private const val TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"
private const val MAX_SOUNDS = 5

class BeatBox(private val assets: AssetManager) {
    val sounds: List<Sound>
    var rate = 1.0f
    private val soundPool = SoundPool.Builder()
        .setMaxStreams(MAX_SOUNDS)
        .build()

    init {
        sounds = loadSounds()
    }

    fun play(sound: Sound) {
        sound.soundId?.let {
            soundPool.play(it, 1.0f, 1.0f, 1, 0, rate)
        }
    }

    fun release() {
        soundPool.release()
    }

    private fun loadSounds(): List<Sound> {
        val soundNames: Array<String>

        try {
            soundNames = assets.list(SOUNDS_FOLDER)!!
        } catch (e: Exception) {
            Log.e(TAG, "Could not list assets", e)
            return emptyList()
        }
        val sounds = mutableListOf<Sound>()
        soundNames.forEach { fileName ->
            val assetsPath = "$SOUNDS_FOLDER/$fileName"
            val sound = Sound(assetsPath)
            try {
                load(sound)
                sounds.add(sound)
            } catch (ioe: IOException) {
                Log.e(TAG, "Could not load sound $fileName", ioe)
            }
        }
        return sounds
    }

    private fun load(sound: Sound) {
        val assetFileDescriptor: AssetFileDescriptor = assets.openFd(sound.assetsPath)
        val soundId = soundPool.load(assetFileDescriptor, 1)
        sound.soundId = soundId
    }
}
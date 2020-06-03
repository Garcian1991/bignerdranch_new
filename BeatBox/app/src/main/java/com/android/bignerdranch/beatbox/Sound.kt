package com.android.bignerdranch.beatbox

private const val WAV = ".wav"

class Sound(val assetsPath: String, var soundId: Int? = null) {
    val name = assetsPath.split("/").last().removeSuffix(WAV)
}
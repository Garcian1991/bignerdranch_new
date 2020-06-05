package com.android.bignerdranch.beatbox

import android.app.Application
import android.content.res.AssetManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class BeatBoxViewModelFactory(
    private val assets: AssetManager,
    private val application: Application
) : ViewModelProvider.AndroidViewModelFactory(application) {
        @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BeatBoxViewModel::class.java)) {
            return BeatBoxViewModel(assets, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
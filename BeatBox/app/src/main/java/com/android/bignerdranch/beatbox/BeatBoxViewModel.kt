package com.android.bignerdranch.beatbox

import android.app.Application
import android.content.res.AssetManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

class BeatBoxViewModel(
    assets: AssetManager,
    application: Application
) : AndroidViewModel(application) {
    val beatBox = BeatBox(assets)
    private val _rate = MutableLiveData<Float>()

    init {
        _rate.value = beatBox.rate
    }

    fun onRateChange(rate: Float) {
        _rate.value = rate
        beatBox.rate = rate
    }

    val rate = Transformations.map(_rate) { rate ->
        val percent = (rate * 100).toInt()
        application.getString(R.string.rate, percent)
    }

    override fun onCleared() {
        super.onCleared()
        beatBox.release()
    }
}
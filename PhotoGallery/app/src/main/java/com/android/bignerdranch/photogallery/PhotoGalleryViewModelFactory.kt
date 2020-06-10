package com.android.bignerdranch.photogallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class PhotoGalleryViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotoGalleryViewModel::class.java)) {
            return PhotoGalleryViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
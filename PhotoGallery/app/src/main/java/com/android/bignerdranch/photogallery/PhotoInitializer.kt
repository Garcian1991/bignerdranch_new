package com.android.bignerdranch.photogallery

import android.provider.ContactsContract
import com.android.bignerdranch.photogallery.api.PhotoResponse
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class PhotoInitializer : JsonDeserializer<PhotoResponse> {
    private val galleryItems: MutableList<GalleryItem> = mutableListOf()

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): PhotoResponse {
        val jsonRootObject = json?.asJsonObject
        val photosObject = jsonRootObject?.get("photos")?.asJsonObject
        val jsonArray = photosObject?.get("photo")?.asJsonArray
        jsonArray?.forEach { jsonElement ->
            val jsonObject = jsonElement.asJsonObject
            galleryItems.add(
                GalleryItem(
                    title = jsonObject.get("title").asString,
                    id = jsonObject.get("id").asString,
                    url = jsonObject.get("url_s").asString
            ))
        }
        return PhotoResponse().apply {
            galleryItems = this@PhotoInitializer.galleryItems
        }
    }
}
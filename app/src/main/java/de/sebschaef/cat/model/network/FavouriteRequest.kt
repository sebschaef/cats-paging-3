package de.sebschaef.cat.model.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FavouriteRequest(
    @Json(name = "image_id") val imageId: String,
    @Json(name = "sub_id") val subId: String
)
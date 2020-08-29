package de.sebschaef.cat.model.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FavouriteItemResponse(
    @Json(name = "id") val id: String?,
    @Json(name = "image") val image: ImageResponse?
)
package de.sebschaef.cat.model.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageResponse(
    @Json(name = "id") val id: String?,
    @Json(name = "url") val url: String?
)
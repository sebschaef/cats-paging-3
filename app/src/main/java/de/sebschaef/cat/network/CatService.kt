package de.sebschaef.cat.network

import de.sebschaef.cat.model.network.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CatService {

    @GET("/v1/images/search")
    suspend fun searchImages(
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 1,
        @Query("order") order: String = "random"
    ): List<ImageResponse>

}
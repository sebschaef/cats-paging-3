package de.sebschaef.cat.network

import de.sebschaef.cat.model.constant.Constants
import de.sebschaef.cat.model.network.FavouriteItemResponse
import de.sebschaef.cat.model.network.FavouriteRequest
import de.sebschaef.cat.model.network.FavouriteResponse
import de.sebschaef.cat.model.network.ImageResponse
import retrofit2.http.*

interface CatService {

    @GET("/v1/images/search")
    suspend fun searchImages(
        @Query("limit") limit: Int = Constants.PAGE_SIZE,
        @Query("page") page: Int = 0,
        @Query("order") order: String = "random"
    ): List<ImageResponse>

    @POST("/v1/favourites")
    suspend fun addFavourite(
        @Body favouriteRequest: FavouriteRequest
    ): FavouriteResponse

    @GET("/v1/favourites")
    suspend fun getFavourites(
        @Query("sub_id") subId: String,
        @Query("limit") limit: Int = Constants.PAGE_SIZE,
        @Query("page") page: Int = 0
    ): List<FavouriteItemResponse>

    @DELETE("/v1/favourites/{favourite_id}")
    suspend fun removeFavourite(
        @Path("favourite_id") favouriteId: String
    )

}
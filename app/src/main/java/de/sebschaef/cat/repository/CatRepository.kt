package de.sebschaef.cat.repository

import de.sebschaef.cat.model.network.FavouriteRequest
import de.sebschaef.cat.model.persistence.Image
import de.sebschaef.cat.network.ApiKeyInterceptor
import de.sebschaef.cat.network.CatService
import de.sebschaef.cat.network.toFavImageList
import de.sebschaef.cat.network.toImageList
import de.sebschaef.cat.persistence.ImagesDatabase
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object CatRepository {

    // TODO Dependency injection
    private val catService by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .build()

        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(CatService::class.java)
    }

    private val favouriteCatImagesDatabase = ImagesDatabase.instance

    suspend fun getRandomCatImages(page: Int, pageSize: Int): List<Image> =
        catService.searchImages(page = page, limit = pageSize)
            .toImageList()
            .map {
                val favImage = favouriteCatImagesDatabase.imagesDao().get(it.id)
                it.copy(
                    isFavoured = favImage?.isFavoured ?: false,
                    favId = favImage?.favId
                )
            }

    suspend fun addFavourite(imageId: String, userId: String) {
        catService.addFavourite(
            FavouriteRequest(imageId = imageId, subId = userId)
        )
    }

    suspend fun getFavourites(userId: String, page: Int, pageSize: Int) =
        catService.getFavourites(
            subId = userId,
            page = page,
            limit = pageSize
        ).toFavImageList()

    suspend fun removeFavourite(favId: String) {
        catService.removeFavourite(favouriteId = favId)
        favouriteCatImagesDatabase.imagesDao().delete(favId = favId)
    }

}
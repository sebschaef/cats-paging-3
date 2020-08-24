package de.sebschaef.cat.repository

import de.sebschaef.cat.model.persistence.Image
import de.sebschaef.cat.network.CatService
import de.sebschaef.cat.network.toImageList
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object CatRepositoryImpl : CatRepository {

    private val catService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient())
            .build()
            .create(CatService::class.java)
    }

    override suspend fun getRandomCatImages(page: Int, pageSize: Int): List<Image> =
        catService.searchImages(page = 1, limit = pageSize).toImageList()

}
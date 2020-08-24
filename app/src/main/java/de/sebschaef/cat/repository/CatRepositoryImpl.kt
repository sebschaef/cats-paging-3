package de.sebschaef.cat.repository

import de.sebschaef.cat.network.CatService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CatRepositoryImpl : CatRepository {

    private val catService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient())
            .build()
            .create(CatService::class.java)
    }

    override suspend fun getRandomCats(page: Int) {
        val response = catService.searchImages()
    }

}
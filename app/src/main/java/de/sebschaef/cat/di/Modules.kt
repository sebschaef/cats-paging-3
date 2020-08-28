package de.sebschaef.cat.di

import androidx.room.Room
import de.sebschaef.cat.network.ApiKeyInterceptor
import de.sebschaef.cat.network.CatService
import de.sebschaef.cat.persistence.ImagesDatabase
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            ImagesDatabase::class.java,
            "favourite_cat_database"
        ).build()
    }

    single {
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

}
package de.sebschaef.cat.repository

interface CatRepository {

    suspend fun getRandomCats(page: Int)

}
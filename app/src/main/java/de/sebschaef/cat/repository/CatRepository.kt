package de.sebschaef.cat.repository

import de.sebschaef.cat.model.persistence.Image

interface CatRepository {

    suspend fun getRandomCatImages(page: Int, pageSize: Int): List<Image>

}
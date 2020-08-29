package de.sebschaef.cat.repository

import de.sebschaef.cat.model.network.FavouriteRequest
import de.sebschaef.cat.model.persistence.Image
import de.sebschaef.cat.network.CatService
import de.sebschaef.cat.network.toFavImageList
import de.sebschaef.cat.network.toImageList
import de.sebschaef.cat.persistence.ImagesDatabase
import org.koin.core.KoinComponent
import org.koin.core.inject

object CatRepository : KoinComponent {

    private val catService: CatService by inject()
    private val imagesDatabase: ImagesDatabase by inject()

    suspend fun getRandomCatImages(page: Int, pageSize: Int): List<Image> =
        catService.searchImages(page = page, limit = pageSize)
            .toImageList()
            .map {
                val favImage = imagesDatabase.imagesDao().get(it.id)
                it.copy(favId = favImage?.favId)
            }

    suspend fun addFavourite(imageId: String, userId: String): String? =
        catService.addFavourite(
            FavouriteRequest(imageId = imageId, subId = userId)
        ).id

    suspend fun getFavourites(userId: String, page: Int, pageSize: Int) =
        catService.getFavourites(
            subId = userId,
            page = page,
            limit = pageSize
        ).toFavImageList()

    suspend fun removeFavourite(favId: String) {
        catService.removeFavourite(favouriteId = favId)
        imagesDatabase.imagesDao().delete(favId = favId)
    }

}
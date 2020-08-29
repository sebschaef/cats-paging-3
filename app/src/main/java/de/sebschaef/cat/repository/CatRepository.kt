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

    /**
     * Queries the cat API for random cat images.
     *
     * @throws Exception from Retrofit -> client request errors, timeouts etc.
     * @param page The page of the images list to query.
     * @param pageSize The page size we use for every query here.
     * @return The list of random cat images for the given page/pageSize as the image persistence
     * model.
     */
    suspend fun getRandomCatImages(page: Int, pageSize: Int): List<Image> =
        catService.searchImages(page = page, limit = pageSize)
            .toImageList()
            .map {
                val favImage = imagesDatabase.imagesDao().get(it.id)
                it.copy(favId = favImage?.favId)
            }

    /**
     * Adds the given image to the given user's favourite cat images.
     *
     * @throws Exception from Retrofit -> client request errors, timeouts etc.
     * @param imageId the id of the image the user wants to favourite.
     * @param userId The id of the user.
     * @return The favId for the favoured image for the given user.
     */
    suspend fun addFavourite(imageId: String, userId: String): String? =
        catService.addFavourite(
            FavouriteRequest(imageId = imageId, subId = userId)
        ).id

    /**
     * Queries the cat API for the given user's favourite cat images.
     *
     * @throws Exception from Retrofit -> client request errors, timeouts etc.
     * @param userId The id of the user.
     * @param page The page of the favourite images list to query.
     * @param pageSize The page size we use for every query here.
     * @return The list of the given user's favourite cat images as the image persistence model.
     */
    suspend fun getFavourites(userId: String, page: Int, pageSize: Int) =
        catService.getFavourites(
            subId = userId,
            page = page,
            limit = pageSize
        ).toFavImageList()

    /**
     * Removes the given image from the given user's favourites in the backend and local database.
     *
     * @throws Exception from Retrofit -> client request errors, timeouts etc.
     * @param favId The id of the favourite item.
     */
    suspend fun removeFavourite(favId: String) {
        catService.removeFavourite(favouriteId = favId)
        imagesDatabase.imagesDao().delete(favId = favId)
    }

}
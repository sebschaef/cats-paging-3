package de.sebschaef.cat.ui.favourites.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import de.sebschaef.cat.model.constant.Constants
import de.sebschaef.cat.model.persistence.Image
import de.sebschaef.cat.persistence.ImagesDatabase
import de.sebschaef.cat.repository.CatRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Fetches a certain page from the favourite cat images API and stores it in the database. The load
 * function is called by the paging library.
 */
@OptIn(ExperimentalPagingApi::class)
class FavouriteCatImagesRemoteMediator : RemoteMediator<Int, Image>(), KoinComponent {

    private val imagesDatabase: ImagesDatabase by inject()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Image>): MediatorResult {
        try {
            val loadPage = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> state.lastItemOrNull()?.let {
                    state.pages.size
                } ?: return MediatorResult.Success(endOfPaginationReached = true)
            }

            val response = CatRepository.getFavourites(
                userId = Constants.USER_ID,
                page = loadPage,
                pageSize = Constants.PAGE_SIZE
            )
            imagesDatabase.imagesDao().insertAll(response)

            return MediatorResult.Success(endOfPaginationReached = response.isEmpty())
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

}
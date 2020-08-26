package de.sebschaef.cat.ui.adapter

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import de.sebschaef.cat.model.persistence.Image
import de.sebschaef.cat.persistence.FavouriteCatImagesDatabase
import de.sebschaef.cat.repository.CatRepository

@OptIn(ExperimentalPagingApi::class)
class FavouriteCatImagesRemoteMediator : RemoteMediator<Int, Image>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Image>): MediatorResult {
        try {
            val loadPage = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> state.lastItemOrNull()?.let {
                    state.pages.size + 1
                } ?: return MediatorResult.Success(endOfPaginationReached = true)
            }

            val response = CatRepository.getRandomCatImages(loadPage, 10) // TODO not random images
            FavouriteCatImagesDatabase.instance.favouriteCatImagesDao().insertAll(response)

            return MediatorResult.Success(endOfPaginationReached = response.isEmpty())
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

}
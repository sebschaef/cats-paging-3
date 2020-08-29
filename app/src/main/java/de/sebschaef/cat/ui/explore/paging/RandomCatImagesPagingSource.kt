package de.sebschaef.cat.ui.explore.paging

import androidx.paging.PagingSource
import de.sebschaef.cat.model.persistence.Image
import de.sebschaef.cat.repository.CatRepository

/**
 * Loads a certain page of the user's favourite images from the database. the load function is
 * called by the paging library.
 */
class RandomCatImagesPagingSource : PagingSource<Int, Image>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> = try {
        val loadPage = params.key ?: 0

        val result = CatRepository.getRandomCatImages(
            page = loadPage,
            pageSize = params.loadSize
        )

        LoadResult.Page(
            data = result,
            prevKey = null,
            nextKey = loadPage + 1
        )
    } catch (e: Exception) {
        LoadResult.Error(e)
    }

}
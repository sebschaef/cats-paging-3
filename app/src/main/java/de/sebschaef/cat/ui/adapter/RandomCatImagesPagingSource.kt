package de.sebschaef.cat.ui.adapter

import androidx.paging.PagingSource
import de.sebschaef.cat.model.persistence.Image
import de.sebschaef.cat.repository.CatRepository

class RandomCatImagesPagingSource : PagingSource<Int, Image>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> = try {
        val nextPage = params.key ?: 1
        val pageSize = params.loadSize
        val result = CatRepository.getRandomCatImages(page = nextPage, pageSize = pageSize)
        LoadResult.Page(
            data = result,
            prevKey = null,
            nextKey = nextPage + 1
        )
    } catch (e: Exception) {
        LoadResult.Error(e)
    }

}
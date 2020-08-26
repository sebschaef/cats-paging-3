package de.sebschaef.cat.ui.favourites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import de.sebschaef.cat.model.event.FavouriteEvent
import de.sebschaef.cat.model.state.FavouriteState
import de.sebschaef.cat.persistence.FavouriteCatImagesDatabase
import de.sebschaef.cat.ui.adapter.FavouriteCatImagesRemoteMediator

class FavouritesViewModel : ViewModel(), FavouriteContract.ViewModel {

    val catImagesFlow = Pager(
        config = PagingConfig(pageSize = 10),
        remoteMediator = FavouriteCatImagesRemoteMediator()
    ) {
        FavouriteCatImagesDatabase.instance.favouriteCatImagesDao().pagingSource()
    }.flow.cachedIn(viewModelScope)

    override val viewState = MutableLiveData<FavouriteState>()

    override fun onViewEvent(favouriteEvent: FavouriteEvent) {
        TODO("Not yet implemented")
    }

}
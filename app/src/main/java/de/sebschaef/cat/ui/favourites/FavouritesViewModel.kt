package de.sebschaef.cat.ui.favourites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import de.sebschaef.cat.model.event.FavouriteEvent
import de.sebschaef.cat.model.event.FavouriteEvent.*
import de.sebschaef.cat.model.persistence.Image
import de.sebschaef.cat.model.state.FavouriteState
import de.sebschaef.cat.persistence.ImagesDatabase
import de.sebschaef.cat.repository.CatRepository
import de.sebschaef.cat.ui.adapter.FavouriteCatImagesRemoteMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouritesViewModel : ViewModel(), FavouriteContract.ViewModel {

    val catImagesFlow = Pager(
        config = PagingConfig(pageSize = 10),
        remoteMediator = FavouriteCatImagesRemoteMediator()
    ) {
        ImagesDatabase.instance.imagesDao().pagingSource()
    }.flow.cachedIn(viewModelScope)

    override val viewState = MutableLiveData<FavouriteState>()

    override fun onViewEvent(favouriteEvent: FavouriteEvent) = when (favouriteEvent) {
        is ImageFavouredChanged -> onImageFavouredChanged(favouriteEvent.image, favouriteEvent.isFavoured)
    }

    private fun onImageFavouredChanged(image: Image, isFavoured: Boolean) {
        if (isFavoured) {
            favourImage(image)
        } else {
            unfavourImage(image)
        }
    }

    private fun favourImage(image: Image) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                CatRepository.addFavourite(
                    imageId = image.id,
                    userId = "cat" // TODO get from resources or a dialog
                )
            } catch (e: Exception) {
                // TODO
                e.printStackTrace()
            }
        }
    }

    private fun unfavourImage(image: Image) {
        image.favId ?: return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                CatRepository.removeFavourite(favId = image.favId)
            } catch (e: Exception) {
                // TODO
                e.printStackTrace()
            }
        }
    }

}
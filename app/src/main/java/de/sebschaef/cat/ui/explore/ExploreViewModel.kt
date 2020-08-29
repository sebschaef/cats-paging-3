package de.sebschaef.cat.ui.explore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import de.sebschaef.cat.model.constant.Constants
import de.sebschaef.cat.model.event.ExploreEvent
import de.sebschaef.cat.model.event.ExploreEvent.*
import de.sebschaef.cat.model.persistence.Image
import de.sebschaef.cat.model.state.ExploreState
import de.sebschaef.cat.repository.CatRepository
import de.sebschaef.cat.ui.adapter.RandomCatImagesPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExploreViewModel : ViewModel(), ExploreContract.ViewModel {

    val catImagesFlow = Pager(config = PagingConfig(pageSize = Constants.PAGE_SIZE)) {
        RandomCatImagesPagingSource()
    }.flow.cachedIn(viewModelScope)

    override val viewState = MutableLiveData<ExploreState>()

    override fun onViewEvent(exploreEvent: ExploreEvent) = when (exploreEvent) {
        is Refresh -> refresh()
        is ImageFavouredChanged -> onImageFavouredChanged(
            position = exploreEvent.position,
            image = exploreEvent.image,
            isFavoured = exploreEvent.isFavoured
        )
    }

    private fun refresh() {
        viewState.value = ExploreState.Load()
    }

    private fun onImageFavouredChanged(position: Int, image: Image, isFavoured: Boolean) {
        if (isFavoured) {
            favourImage(position, image)
        } else {
            unfavourImage(position, image)
        }
    }

    private fun favourImage(position: Int, image: Image) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val id = CatRepository.addFavourite(
                    imageId = image.id,
                    userId = Constants.USER_ID
                )
                id?.let { image.favId = it }
                viewState.postValue(ExploreState.Load(position))
            } catch (e: Exception) {
                viewState.postValue(ExploreState.Error())
            }
        }
    }

    private fun unfavourImage(position: Int, image: Image) {
        val favId = image.favId ?: return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                CatRepository.removeFavourite(favId)
                image.favId = null
                viewState.postValue(ExploreState.Load(position))
            } catch (e: Exception) {
                viewState.postValue(ExploreState.Error())
            }
        }
    }

}
package de.sebschaef.cat.ui.explore

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import de.sebschaef.cat.model.event.ExploreEvent
import de.sebschaef.cat.model.event.ExploreEvent.*
import de.sebschaef.cat.model.persistence.Image
import de.sebschaef.cat.model.state.ExploreState
import de.sebschaef.cat.repository.CatRepository
import de.sebschaef.cat.ui.adapter.RandomCatImagesPagingSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExploreViewModel : ViewModel(), ExploreContract.ViewModel {

    // TODO make part of state flow
    val catImagesFlow = Pager(config = PagingConfig(pageSize = 10)) {
        RandomCatImagesPagingSource()
    }.flow.cachedIn(viewModelScope)

    override val viewState = MutableLiveData<ExploreState>()

    override fun onViewEvent(exploreEvent: ExploreEvent) = when (exploreEvent) {
        is ImageFavouredChanged -> onImageFavouredChanged(exploreEvent.image, exploreEvent.isFavoured)
    }

    private fun onImageFavouredChanged(image: Image, isFavoured: Boolean) {
        if (isFavoured) {
            favourImage(image)
        } else {
            TODO()
        }
    }

    private fun favourImage(image: Image) {
        viewModelScope.launch(Dispatchers.IO) {
            CatRepository.addFavourite(
                imageId = image.id,
                userId = "cat" // TODO get from resources or a dialog
            )
        }
    }

}
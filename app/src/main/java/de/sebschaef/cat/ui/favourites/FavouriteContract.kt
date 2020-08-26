package de.sebschaef.cat.ui.favourites

import androidx.lifecycle.MutableLiveData
import de.sebschaef.cat.model.event.FavouriteEvent
import de.sebschaef.cat.model.state.FavouriteState

interface FavouriteContract {

    interface View {
        fun render(favouriteState: FavouriteState)
    }

    interface ViewModel {
        val viewState: MutableLiveData<FavouriteState>
        fun onViewEvent(favouriteEvent: FavouriteEvent)
    }

}
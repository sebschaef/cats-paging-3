package de.sebschaef.cat.ui.explore

import androidx.lifecycle.MutableLiveData
import de.sebschaef.cat.model.event.ExploreEvent
import de.sebschaef.cat.model.state.ExploreState

interface ExploreContract {

    interface View {
        fun render(exploreState: ExploreState)
    }

    interface ViewModel {
        val viewState: MutableLiveData<ExploreState>
        fun onViewEvent(exploreEvent: ExploreEvent)
    }

}
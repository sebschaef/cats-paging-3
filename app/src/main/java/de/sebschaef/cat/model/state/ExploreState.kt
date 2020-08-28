package de.sebschaef.cat.model.state

sealed class ExploreState {
    data class Load(val position: Int? = null) : ExploreState()
    data class Error(val message: String) : ExploreState()
}
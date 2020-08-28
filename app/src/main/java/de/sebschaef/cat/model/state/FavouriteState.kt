package de.sebschaef.cat.model.state

sealed class FavouriteState {
    data class Load(val position: Int?) : FavouriteState()
}
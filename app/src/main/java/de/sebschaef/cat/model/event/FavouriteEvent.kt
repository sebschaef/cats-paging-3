package de.sebschaef.cat.model.event

import de.sebschaef.cat.model.persistence.Image

sealed class FavouriteEvent {
    object Refresh : FavouriteEvent()

    data class ImageFavouredChanged(
        val position: Int,
        val image: Image,
        val isFavoured: Boolean
    ) : FavouriteEvent()
}
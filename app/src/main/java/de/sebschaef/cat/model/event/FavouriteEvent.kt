package de.sebschaef.cat.model.event

import de.sebschaef.cat.model.persistence.Image

sealed class FavouriteEvent {
    data class ImageFavouredChanged(
        val image: Image,
        val isFavoured: Boolean
    ) : FavouriteEvent()
}
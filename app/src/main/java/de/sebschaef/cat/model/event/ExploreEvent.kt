package de.sebschaef.cat.model.event

import de.sebschaef.cat.model.persistence.Image

sealed class ExploreEvent {
    object Refresh : ExploreEvent()

    data class ImageFavouredChanged(
        val position: Int,
        val image: Image,
        val isFavoured: Boolean
    ) : ExploreEvent()
}
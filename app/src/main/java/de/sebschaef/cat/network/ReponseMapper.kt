package de.sebschaef.cat.network

import de.sebschaef.cat.model.network.FavouriteItemResponse
import de.sebschaef.cat.model.network.ImageResponse
import de.sebschaef.cat.model.persistence.Image

fun List<ImageResponse>.toImageList(): List<Image> =
    asSequence()
        .filterNotNull()
        .map {
            Image(id = it.id, url = it.url)
        }
        .toList()

fun List<FavouriteItemResponse>.toFavImageList(): List<Image> =
    asSequence()
        .filterNotNull()
        .map {
            Image(
                favId = it.id,
                id = it.image.id,
                url = it.image.url
            )
        }
        .toList()
package de.sebschaef.cat.network

import de.sebschaef.cat.model.network.ImageResponse
import de.sebschaef.cat.model.persistence.Image

fun List<ImageResponse>.toImageList(): List<Image> =
    asSequence()
        .filterNotNull()
        .map { Image(it.id, it.url) }
        .toList()
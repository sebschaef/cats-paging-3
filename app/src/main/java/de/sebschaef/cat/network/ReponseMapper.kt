package de.sebschaef.cat.network

import de.sebschaef.cat.model.network.FavouriteItemResponse
import de.sebschaef.cat.model.network.ImageResponse
import de.sebschaef.cat.model.persistence.Image

/**
 * Transforms the given list of images from their network response model to the persistence
 * response model.
 * The mapper filters out null objects and objects with null properties.
 *
 * @receiver The list of image response models.
 * @return A null filtered list of image persistence models.
 */
fun List<ImageResponse>.toImageList(): List<Image> =
    asSequence()
        .filterNotNull()
        .filter { it.id != null && it.url != null }
        .map { Image(id = it.id!!, url = it.url!!) }
        .toList()

/**
 * Transforms the given list of favourite items from their network response model to the image
 * persistence response model.
 * The mapper filters out null objects and objects with null properties.
 *
 * @receiver The list of favourite item response models.
 * @return A null filtered list of image persistence models.
 */
fun List<FavouriteItemResponse>.toFavImageList(): List<Image> =
    asSequence()
        .filterNotNull()
        .filter { it.id != null && it.image?.id != null && it.image.url != null }
        .map {
            Image(
                favId = it.id,
                id = it.image!!.id!!,
                url = it.image.url!!
            )
        }
        .toList()
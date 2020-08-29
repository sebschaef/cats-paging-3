package de.sebschaef.cat

import de.sebschaef.cat.model.network.FavouriteItemResponse
import de.sebschaef.cat.model.network.ImageResponse
import de.sebschaef.cat.network.toFavImageList
import de.sebschaef.cat.network.toImageList
import org.junit.Test
import org.junit.Assert.*

class ResponseMapperUnitTest {

    // region toImageList()

    @Test
    fun `test network model object with null property should not be contained in result`() {
        assertFalse(
            listOf(
                ImageResponse(id = "image0", url = "http://www.google.com/pixel"),
                ImageResponse(id = "dfgdfgs", url = "http://www.apple.com/iphone"),
                ImageResponse(id = null, url = "http://www.nokia.com/3210")
            ).toImageList().any { it.url == "http://www.nokia.com/3210" }
        )

        assertFalse(
            listOf(
                ImageResponse(id = "image0", url = "http://www.google.com/pixel"),
                ImageResponse(id = "dfgdfgs", url = "http://www.apple.com/iphone"),
                ImageResponse(id = "id", url = null)
            ).toImageList().any { it.id == "id" }
        )
    }

    @Test
    fun `test result list should have the same item order as network model list`() {
        val result = listOf(
            ImageResponse(id = "image0", url = "http://www.google.com/pixel"),
            ImageResponse(id = "dfgdfgs", url = "http://www.apple.com/iphone"),
            ImageResponse(id = "id", url = "http://www.nokia.com/3210")
        ).toImageList()

        assertTrue(result[0].id == "image0")
        assertTrue(result[1].id == "dfgdfgs")
        assertTrue(result[2].id == "id")
    }

    // endregion

    // region toFavImageList()

    @Test
    fun `test network model object with null property should not be contained in fav result`() {
        assertFalse(
            listOf(
                FavouriteItemResponse(
                    id = null,
                    image = ImageResponse(id = "image0", url = "http://www.google.com/pixel")
                ),
                FavouriteItemResponse(
                    id = "dog",
                    image = ImageResponse(id = "dfgdfgs", url = "http://www.apple.com/iphone")
                ),
                FavouriteItemResponse(
                    id = "bird",
                    image = ImageResponse(id = null, url = "http://www.nokia.com/3210")
                )
            ).toFavImageList().any { it.id == "image0" }
        )

        assertFalse(
            listOf(
                FavouriteItemResponse(
                    id = "cat",
                    image = ImageResponse(id = "image0", url = "http://www.google.com/pixel")
                ),
                FavouriteItemResponse(
                    id = "dog",
                    image = ImageResponse(id = "dfgdfgs", url = "http://www.apple.com/iphone")
                ),
                FavouriteItemResponse(
                    id = "bird",
                    image = ImageResponse(id = null, url = "http://www.nokia.com/3210")
                )
            ).toFavImageList().any { it.url == "http://www.nokia.com/3210" }
        )

        assertFalse(
            listOf(
                FavouriteItemResponse(
                    id = "cat",
                    image = ImageResponse(id = "image0", url = "http://www.google.com/pixel")
                ),
                FavouriteItemResponse(
                    id = "dog",
                    image = ImageResponse(id = "dfgdfgs", url = null)
                ),
                FavouriteItemResponse(
                    id = "bird",
                    image = ImageResponse(id = "id", url = "http://www.nokia.com/3210")
                )
            ).toFavImageList().any { it.id == "dfgdfgs" }
        )
    }

    @Test
    fun `test fav result list should have the same item order as network model list`() {
        val result = listOf(
            FavouriteItemResponse(
                id = "cat",
                image = ImageResponse(id = "image0", url = "http://www.google.com/pixel")
            ),
            FavouriteItemResponse(
                id = "dog",
                image = ImageResponse(id = "dfgdfgs", url = "http://www.apple.com/iphone")
            ),
            FavouriteItemResponse(
                id = "bird",
                image = ImageResponse(id = "id", url = "http://www.nokia.com/3210")
            )
        ).toFavImageList()

        assertTrue(result[0].favId == "cat")
        assertTrue(result[1].favId == "dog")
        assertTrue(result[2].favId == "bird")
    }

    // endregion

}
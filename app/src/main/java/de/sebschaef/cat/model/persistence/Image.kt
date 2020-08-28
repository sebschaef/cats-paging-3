package de.sebschaef.cat.model.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_cat_images")
data class Image(
    @PrimaryKey val id: String,
    val url: String,
    var favId: String? = null,
    var isFavoured: Boolean = false // TODO remove and use favId only?
)
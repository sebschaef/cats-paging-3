package de.sebschaef.cat.model.persistence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_cat_images")
data class Image(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "url") val url: String
)
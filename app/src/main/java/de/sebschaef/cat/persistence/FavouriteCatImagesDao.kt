package de.sebschaef.cat.persistence

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.sebschaef.cat.model.persistence.Image

@Dao
interface FavouriteCatImagesDao {

    @Query("SELECT * FROM favourite_cat_images")
    fun getAll(): List<Image>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<Image>)

    @Query("SELECT * FROM favourite_cat_images")
    fun pagingSource(): PagingSource<Int, Image>

    @Query("DELETE FROM favourite_cat_images")
    suspend fun clearAll()

}
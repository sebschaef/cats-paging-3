package de.sebschaef.cat.persistence

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.sebschaef.cat.model.persistence.Image

@Dao
interface ImagesDao {

    @Query("SELECT * FROM favourite_cat_images")
    suspend fun getAll(): List<Image>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<Image>)

    @Query("SELECT * FROM favourite_cat_images")
    fun pagingSource(): PagingSource<Int, Image>

    @Query("DELETE FROM favourite_cat_images")
    suspend fun clearAll()

    @Query("SELECT EXISTS (SELECT * FROM favourite_cat_images WHERE id = :imageId LIMIT 1)")
    suspend fun contains(imageId: String): Boolean

}
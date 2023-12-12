package com.demo.sweetfish.logic.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.demo.sweetfish.logic.model.ImageSource

@Dao
interface ImageSourceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(imageSource: ImageSource)

    @Delete
    fun delete(imageSource: ImageSource)

    @Query("delete from ImageSource")
    fun deleteAll()

    @Query("select * from ImageSource where id=:id")
    fun findById(id: Long): ImageSource?

}
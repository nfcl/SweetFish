package com.demo.sweetfish.logic.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.demo.sweetfish.logic.model.GoodsPreviewImage

@Dao
interface GoodsPreviewImageDao {

    @Insert
    fun insert(goodsPreviewImage: GoodsPreviewImage): Long

    @Delete
    fun delete(goodsPreviewImage: GoodsPreviewImage)

    @Query("select * from GoodsPreviewImage where goodsId=:goodsId order by id asc")
    fun findByGoodsId(goodsId: Long): List<GoodsPreviewImage>

    @Query("select * from GoodsPreviewImage where goodsId=:goodsId order by id asc limit 1")
    fun findByGoodsIdLimitPreview(goodsId: Long): GoodsPreviewImage

}
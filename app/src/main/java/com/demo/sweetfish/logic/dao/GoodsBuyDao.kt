package com.demo.sweetfish.logic.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.demo.sweetfish.logic.model.GoodsBuy

@Dao
interface GoodsBuyDao {

    @Insert
    fun insert(goodsBuy: GoodsBuy): Long

    @Query("select * from GoodsBuy where buyerId=:buyerId")
    fun findByBuyerId(buyerId: Long): List<GoodsBuy>

}
package com.demo.sweetfish.logic.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.demo.sweetfish.logic.model.ChatInfo

@Dao
interface ChatInfoDao {

    @Insert
    fun insert(chatInfoDao: ChatInfo)

    @Delete
    fun delete(id: Long)

    @Update
    fun update(chatInfo: ChatInfo)

    @Query("select * from chatInfo where goodsId in (select id from Goods where sellerId=:sellerId) ")
    fun findBySellerId(sellerId: Long): List<ChatInfo>

    @Query("select * from chatInfo where buyerId=:buyerId ")
    fun findByBuyerId(buyerId: Long): List<ChatInfo>

}
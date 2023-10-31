package com.demo.sweetfish.logic.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.demo.sweetfish.logic.model.Message

@Dao
interface MessageDao {

    @Insert
    fun insert(message: Message)

    @Delete
    fun delete(id: Long)

    @Update
    fun update(message: Message)

    @Query("select * from message where senderId=:senderId and receiverId=:receiverId")
    fun findBySenderAndReceiver(senderId: Long, receiverId: Long): List<Message>

}
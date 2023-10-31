package com.demo.sweetfish.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * @property content 消息内容
 * @property senderId 发送者Id
 * @property receiverId 接收者Id
 * @property sendTime 发送时间
 */
@Entity
data class Message(
    val content: String,
    val senderId: Long,
    val receiverId: Long,
    val sendTime: Date,
) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    fun foo() {
        if (sendTime != Date()) {

        }
    }

}
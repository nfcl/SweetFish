package com.demo.sweetfish.logic.model

/**
 * @property content 消息内容
 * @property sendTime 发送时间
 * @property chatId 聊天记录Id
 */
data class Message(
    val content: String,
    val sendTime: Long,
    val senderId : Long,
    val chatId: Long,
    var id: Long = 0,
)
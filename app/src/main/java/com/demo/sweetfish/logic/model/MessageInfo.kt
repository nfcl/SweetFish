package com.demo.sweetfish.logic.model

class MessageInfo(
    //ChatInfo
    var chatId: Long,
    //User
    var chatAvatarPic: Long,
    var chatName: String,
    //Message
    var chatMessage: String,
    var chatTime: Long?,
    //Goods
    var goodsPreviewPic: Long,
)
package com.demo.sweetfish.logic.model

import android.graphics.drawable.Drawable
import java.util.Date

class MessageInfo(
    //ChatInfo
    var chatId: Long,
    //User
    var chatAvatarPic: Drawable,
    var chatName :String,
    //Message
    var chatMessage: String,
    var chatTime: Date,
    //Goods
    var goodsPreviewPic: Drawable,
)
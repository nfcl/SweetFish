package com.demo.sweetfish.logic.dao

import androidx.room.Dao
import androidx.room.Query
import com.demo.sweetfish.logic.model.MessageInfo

@Dao
interface MessageInfoDao {@Query("""
     select 
        ChatInfo.id as chatId,
        (select case when User.name is not null then User.name else User.id end as chatName, User.avatarPic as chatAvatarPic from User where User.id=ChatInfo.buyerId),
        (select Message.content as chatMessage,Message.sendTime as chatTime from Message where Message.chatId=ChatInfo.id order by Message.sendTime desc limit 1),
        (select GoodsPreviewImage.content as goodsPreviewPic from GoodsPreviewImage where GoodsPreviewImage.goodsId=ChatInfo.goodsId order by GoodsPreviewImage.id asc limit 1) 
    from 
        ChatInfo 
        inner join Goods on ChatInfo.goodsId=Goods.id 
    where 
        Goods.sellerId=:id
""")
fun findBuyerByUserId(id: Long): List<MessageInfo>

    @Query("""
     select 
        ChatInfo.id as chatId,
        (select case when User.name is not null then User.name else User.id end as chatName, User.avatarPic as chatAvatarPic from User where User.id=Goods.sellerId),
        (select Message.content as chatMessage,Message.sendTime as chatTime from Message where Message.chatId=ChatInfo.id order by Message.sendTime desc limit 1),
        (select GoodsPreviewImage.content as goodsPreviewPic from GoodsPreviewImage where GoodsPreviewImage.goodsId=ChatInfo.goodsId order by GoodsPreviewImage.id asc limit 1) 
    from 
        ChatInfo 
        inner join Goods on ChatInfo.goodsId=Goods.id 
    where 
        ChatInfo.buyerId=:id
""")
    fun findSellerByUserId(id: Long): List<MessageInfo>
}
package com.demo.sweetfish.logic.dao

import androidx.room.Dao
import androidx.room.Query
import com.demo.sweetfish.logic.model.GoodsBuyInfo

@Dao
interface GoodsBuyInfoDao {

    @Query("""
        select 
            GoodsBuy.id as goodsBuyId,
            GoodsBuy.buyTime as goodsBuyTime,
            Goods.sellerId as sellerId,
            (select User.name from User where User.id=Goods.sellerId) as sellerName,
            (select User.avatarPic from User where User.id=Goods.sellerId) as sellerAvatar,
            Goods.id as goodsId,
            Goods.title as goodsName,
            (select GoodsPreviewImage.content from GoodsPreviewImage where GoodsPreviewImage.goodsId=Goods.id order by GoodsPreviewImage.id asc limit 1) as goodsPreview,
            Goods.price as goodsPrice
        from 
            GoodsBuy inner join Goods on GoodsBuy.goodsId=Goods.id
        where
            GoodsBuy.buyerId=:buyerId
    """)
    fun findByBuyerId(buyerId: Long): List<GoodsBuyInfo>

}
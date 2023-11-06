package com.demo.sweetfish.logic.dao

import androidx.room.Dao
import androidx.room.Query
import com.demo.sweetfish.logic.model.GoodsWithSellerInfo

@Dao
interface GoodsWithSellerInfoDao {

    @Query(
        """ select 
                Goods.id as goodsId,
                Goods.title as goodsTitle,
                Goods.price as goodsPrice,
                (select content from GoodsPreviewImage where goodsId=Goods.id order by id asc limit 1) as goodsPreviewPic,
                User.id as sellerId,
                User.name as sellerName,
                User.avatarPic as sellerAvatarPic 
            from 
                Goods inner join User on Goods.sellerId=User.id"""
    )
    fun findAll(): List<GoodsWithSellerInfo>

    @Query(
        """ select 
                Goods.id as goodsId,
                Goods.title as goodsTitle,
                Goods.price as goodsPrice,
                (select content from GoodsPreviewImage where goodsId=Goods.id order by id asc limit 1) as goodsPreviewPic,
                User.id as sellerId,
                User.name as sellerName,
                User.avatarPic as sellerAvatarPic 
            from 
                Goods inner join User on Goods.sellerId=User.id 
            where 
                Goods.id in (:id)"""
    )
    fun findByGoodsIds(id: LongArray): List<GoodsWithSellerInfo>

    @Query(
        """ select 
                Goods.id as goodsId,
                Goods.title as goodsTitle,
                Goods.price as goodsPrice,
                (select content from GoodsPreviewImage where goodsId=Goods.id order by id asc limit 1) as goodsPreviewPic,
                User.id as sellerId,
                User.name as sellerName,
                User.avatarPic as sellerAvatarPic 
            from 
                Goods inner join User on Goods.sellerId=User.id 
            where 
                Goods.title like (:goodsName)"""
    )
    fun findLikeGoodsName(goodsName: String): List<GoodsWithSellerInfo>

}
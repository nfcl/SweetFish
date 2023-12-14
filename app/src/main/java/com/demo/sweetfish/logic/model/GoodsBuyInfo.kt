package com.demo.sweetfish.logic.model

import java.util.Date

data class GoodsBuyInfo(
    //GoodsBuy
    val goodsBuyId: Long,
    val goodsBuyState: GoodsState,
    val goodsBuyTime: Date,
    //User
    val sellerId: Long,
    val sellerName: String?,
    val sellerAvatar: Long,
    //Goods
    val goodsId: Long,
    val goodsName: String,
    val goodsPreview: Long,
    val goodsPrice: Double,
)
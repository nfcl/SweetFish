package com.demo.sweetfish.logic.model

import android.graphics.drawable.Drawable
import java.util.Date

data class GoodsBuyInfo(
    //GoodsBuy
    val goodsBuyId: Long,
    val goodsBuyTime: Date,
    //User
    val sellerId: Long,
    val sellerName: String?,
    val sellerAvatar: Drawable,
    //Goods
    val goodsId: Long,
    val goodsName: String,
    val goodsPreview: Drawable,
    val goodsPrice: Double,
)
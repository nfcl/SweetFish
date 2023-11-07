package com.demo.sweetfish.logic.model

import android.graphics.drawable.Drawable

data class GoodsWithSellerInfo(
    val goodsId: Long,
    val goodsTitle: String,
    val goodsPrice: Double,
    val goodsPreviewPic: Drawable,
    val sellerId: Long,
    val sellerName: String,
    val sellerAvatarPic: Drawable,
)
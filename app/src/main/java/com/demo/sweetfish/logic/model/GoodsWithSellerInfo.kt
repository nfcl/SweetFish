package com.demo.sweetfish.logic.model

import android.graphics.drawable.Drawable

data class GoodsWithSellerInfo(
    val goodsId: Long,
    val goodsTitle: String,
    val goodsPrice: Double,
    val goodsPreviewPic: Drawable?, //TODO 由于发布商品功能还未增加添加预览图功能，因此将预览图改成Nullable
    val sellerId: Long,
    val sellerName: String?,
    val sellerAvatarPic: Drawable,
)
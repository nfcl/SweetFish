package com.demo.sweetfish.logic.model

data class GoodsWithSellerInfo(
    val goodsId: Long,
    val goodsTitle: String,
    val goodsPrice: Double,
    val goodsPreview: Long,
    val sellerId: Long,
    val sellerName: String?,
    val sellerAvatar: Long,
)
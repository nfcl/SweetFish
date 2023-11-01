package com.demo.sweetfish.logic.model

import android.graphics.drawable.Drawable
import utils.DrawableUtils

data class GoodsWithSellerInfo(
    val goodsId: Long,
    val goodsTitle: String,
    val goodsPrice: Double,
    val goodsPreviewPic: ByteArray,
    val sellerId: Long,
    val sellerName: String,
    val sellerAvatarPic: ByteArray,
) {

    fun getSellerAvatar(): Drawable {
        return DrawableUtils.createDrawableFromByteArray(sellerAvatarPic!!, "sellerAvatarPic")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GoodsWithSellerInfo

        if (goodsId != other.goodsId) return false
        if (goodsTitle != other.goodsTitle) return false
        if (goodsPrice != other.goodsPrice) return false
        if (!goodsPreviewPic.contentEquals(other.goodsPreviewPic)) return false
        if (sellerId != other.sellerId) return false
        if (sellerName != other.sellerName) return false
        if (!sellerAvatarPic.contentEquals(other.sellerAvatarPic)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = goodsId.hashCode()
        result = 31 * result + goodsTitle.hashCode()
        result = 31 * result + goodsPrice.hashCode()
        result = 31 * result + goodsPreviewPic.contentHashCode()
        result = 31 * result + sellerId.hashCode()
        result = 31 * result + sellerName.hashCode()
        result = 31 * result + sellerAvatarPic.contentHashCode()
        return result
    }
}
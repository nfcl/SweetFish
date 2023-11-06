package com.demo.sweetfish.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GoodsPreviewImage(
    val goodsId: Long,
    val content: ByteArray,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GoodsPreviewImage

        if (goodsId != other.goodsId) return false
        if (!content.contentEquals(other.content)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = goodsId.hashCode()
        result = 31 * result + content.contentHashCode()
        return result
    }
}
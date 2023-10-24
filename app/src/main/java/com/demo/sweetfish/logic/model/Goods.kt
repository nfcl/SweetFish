package com.demo.sweetfish.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Goods(
    var title: String,
    var price: Double,
    var sellerId: Long,
    var previewPic: ByteArray
) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Goods

        if (title != other.title) return false
        if (price != other.price) return false
        if (sellerId != other.sellerId) return false
        if (!previewPic.contentEquals(other.previewPic)) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + price.hashCode()
        result = 31 * result + sellerId.hashCode()
        result = 31 * result + previewPic.contentHashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}
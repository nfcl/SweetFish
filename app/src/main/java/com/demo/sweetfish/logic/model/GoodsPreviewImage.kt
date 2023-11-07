package com.demo.sweetfish.logic.model

import android.graphics.drawable.Drawable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GoodsPreviewImage(
    val goodsId: Long,
    val content: Drawable,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
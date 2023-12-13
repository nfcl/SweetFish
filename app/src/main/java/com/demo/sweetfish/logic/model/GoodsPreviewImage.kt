package com.demo.sweetfish.logic.model

import android.graphics.drawable.Drawable

data class GoodsPreviewImage(
    val goodsId: Long,
    val content: Drawable,
    var id: Long = 0,
)
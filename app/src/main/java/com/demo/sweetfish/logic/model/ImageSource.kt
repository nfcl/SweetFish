package com.demo.sweetfish.logic.model

import android.graphics.drawable.Drawable
import androidx.room.Entity

@Entity(primaryKeys = ["id"])
data class ImageSource(
    var content: Drawable?,
    var id: Long = 0,
)
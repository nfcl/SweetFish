package com.demo.sweetfish.logic.convert

import android.graphics.drawable.Drawable
import androidx.room.TypeConverter
import utils.DrawableUtils
import utils.DrawableUtils.Companion.toBytes

class Converters {

    @TypeConverter
    fun byteArrayToDrawable(value: ByteArray?): Drawable? {
        return value?.let { DrawableUtils.createDrawableFromByteArray(value, "") }
    }

    @TypeConverter
    fun drawableToByteArray(drawable: Drawable?): ByteArray? {
        return drawable?.let { drawable.toBytes() }
    }

}
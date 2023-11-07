package com.demo.sweetfish.logic.convert

import android.graphics.drawable.Drawable
import androidx.room.TypeConverter
import utils.DrawableUtils
import utils.DrawableUtils.Companion.toBytes
import java.util.Date

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromByteArray(value: ByteArray?): Drawable? {
        return value?.let { DrawableUtils.createDrawableFromByteArray(value, "") }
    }

    @TypeConverter
    fun drawableToByteArray(drawable: Drawable?): ByteArray? {
        return drawable?.let { drawable.toBytes() }
    }
}
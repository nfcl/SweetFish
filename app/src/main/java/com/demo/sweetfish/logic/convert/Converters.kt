package com.demo.sweetfish.logic.convert

import android.graphics.drawable.Drawable
import androidx.room.TypeConverter
import com.demo.sweetfish.logic.model.UserPositionInfo
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

    @TypeConverter
    fun fromByteArray(value: String?): UserPositionInfo? {
        return value?.let {
            val array = it.split(" ")
            return UserPositionInfo(array[0], array[1], array[2])
        }
    }

    @TypeConverter
    fun drawableToByteArray(positionInfo: UserPositionInfo?): String? {
        return positionInfo?.let { "${positionInfo.province} ${positionInfo.city} ${positionInfo.district}" }
    }
}
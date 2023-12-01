package com.demo.sweetfish.logic.convert

import android.graphics.drawable.Drawable
import androidx.room.TypeConverter
import com.demo.sweetfish.logic.model.GoodsState
import com.demo.sweetfish.logic.model.UserLocation
import utils.DrawableUtils
import utils.DrawableUtils.Companion.toBytes
import java.util.Date

class Converters {

    @TypeConverter
    fun timestampToDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun byteArrayToDrawable(value: ByteArray?): Drawable? {
        return value?.let { DrawableUtils.createDrawableFromByteArray(value, "") }
    }

    @TypeConverter
    fun drawableToByteArray(drawable: Drawable?): ByteArray? {
        return drawable?.let { drawable.toBytes() }
    }

    @TypeConverter
    fun locationStringsToUserLocation(value: String?): UserLocation? {
        return value?.let {
            val array = it.split(" ")
            return UserLocation(array[0], array[1], array[2])
        }
    }

    @TypeConverter
    fun userLocationToLocationString(positionInfo: UserLocation?): String? {
        return positionInfo?.let { "${positionInfo.province} ${positionInfo.city} ${positionInfo.district}" }
    }
}
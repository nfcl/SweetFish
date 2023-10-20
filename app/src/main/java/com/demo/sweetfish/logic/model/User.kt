package com.demo.sweetfish.logic.model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.ByteArrayOutputStream
import java.sql.Blob


/**
 * 用户类
 *
 * 用于保存用户信息
 * @property id         唯一标识符
 * @property name       昵称
 * @property sex        性别
 * @property account    账号
 * @property password   密码
 * @property avatarBlob 头像的二进制数据
 */
@Entity
data class User(
    var name: String,
    var sex: Boolean,
    var account: String,
    var password: String,
    private var avatarBlob: Blob
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    /**
     * 获得头像图片
     * @return 返回一个[Drawable]作为图片对象
     */
    fun getAvatar(): Drawable? {
        return Drawable.createFromStream(avatarBlob.binaryStream, "img")
    }

    /**
     * 设置头像图片
     * @param drawable 设置的头像图片
     */
    fun setAvatar(drawable: Drawable?) {
        if (drawable == null) {
            return
        }
        val bitmap = Bitmap
            .createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        avatarBlob.setBytes(0, stream.toByteArray())
    }
}
package com.demo.sweetfish.logic.model

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.ByteArrayOutputStream
import java.sql.Blob

@Entity
data class Goods(
    var title: String,
    var price: Double,
    var sellerId: Long,
    private var previewPicBlob: Blob
) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    /**
     * 获得预览图片
     * @return 返回一个[Drawable]作为图片对象
     */
    fun getPreviewPic(): Drawable? {
        return Drawable.createFromStream(previewPicBlob.binaryStream, "img")
    }

    /**
     * 设置预览图片
     * @param drawable 设置的头像图片
     */
    fun setPreviewPic(drawable: Drawable?) {
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
        previewPicBlob.setBytes(0, stream.toByteArray())
    }
}
package utils

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import java.io.ByteArrayOutputStream
import java.time.Instant
import kotlin.random.Random

abstract class DrawableUtils {

    companion object {
        fun getGradientDrawable(
            sizeX: Int = 100,
            sizeY: Int = 100,
            colors: IntArray = intArrayOf(
                Random(Instant.now().epochSecond).nextInt() % 0xFFFFFF,
                Random(Instant.now().epochSecond).nextInt() % 0xFFFFFF
            ),
        ): Drawable {
            with(GradientDrawable()) {
                setSize(sizeX, sizeY)
                orientation = GradientDrawable.Orientation.TL_BR
                this.colors = colors
                return this
            }
        }

        fun Drawable.toBytes(): ByteArray {
            val bitmap = (this as BitmapDrawable).bitmap
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            return stream.toByteArray()
        }

        fun createDrawableFromByteArray(byteArray: ByteArray, drawableName: String): Drawable {
            return Drawable.createFromStream(byteArray.inputStream(), drawableName)!!
        }
    }
}
package com.demo.sweetfish.ui.goodsPage

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewTreeObserver.OnPreDrawListener
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import com.example.sweetfish.R

class GoodsPageActivityPreviewPictureView(context: Context, attrs: AttributeSet) :
    GridLayout(context, attrs) {

    data class GridInfo(
        val col: Int,
        val row: Int,
        val colSpan: Int,
        val rowSpan: Int,
    )

    companion object {

        val gridInfoList = listOf(
            listOf(
                GridInfo(0, 0, 6, 6),
            ), listOf(
                GridInfo(0, 0, 6, 6),
                GridInfo(0, 6, 6, 6),
            ), listOf(
                GridInfo(0, 0, 6, 6),
                GridInfo(0, 6, 3, 3),
                GridInfo(3, 6, 3, 3),
            ), listOf(
                GridInfo(0, 0, 6, 6),
                GridInfo(0, 6, 4, 4),
                GridInfo(4, 6, 2, 2),
                GridInfo(4, 8, 2, 2),
            ), listOf(
                GridInfo(0, 0, 6, 6),
                GridInfo(0, 6, 3, 3),
                GridInfo(3, 6, 3, 3),
                GridInfo(0, 9, 3, 3),
                GridInfo(3, 9, 3, 3),
            ), listOf(
                GridInfo(0, 0, 6, 6),
                GridInfo(0, 6, 3, 3),
                GridInfo(3, 6, 3, 3),
                GridInfo(0, 9, 2, 2),
                GridInfo(2, 9, 2, 2),
                GridInfo(4, 9, 2, 2),
            ), listOf(
                GridInfo(0, 0, 6, 6),
                GridInfo(0, 6, 4, 4),
                GridInfo(4, 6, 2, 2),
                GridInfo(4, 8, 2, 2),
                GridInfo(0, 10, 2, 2),
                GridInfo(2, 10, 2, 2),
                GridInfo(4, 10, 2, 2),
            ), listOf(
                GridInfo(0, 0, 6, 6),
                GridInfo(0, 6, 3, 3),
                GridInfo(3, 6, 3, 3),
                GridInfo(0, 9, 3, 3),
                GridInfo(3, 9, 3, 3),
                GridInfo(0, 12, 2, 2),
                GridInfo(2, 12, 2, 2),
                GridInfo(4, 12, 2, 2)
            ), listOf(
                GridInfo(0, 0, 6, 6),
                GridInfo(0, 6, 6, 6),
                GridInfo(0, 12, 3, 3),
                GridInfo(3, 12, 3, 3),
                GridInfo(0, 15, 3, 3),
                GridInfo(3, 15, 3, 3),
                GridInfo(0, 18, 2, 2),
                GridInfo(2, 18, 2, 2),
                GridInfo(4, 18, 2, 2)
            )
        )

    }

    fun setImageList(data: List<Drawable?>) {

        removeAllViews()

        if (data.isEmpty()) {
            return
        }

        val layoutInfo = if (data.size > gridInfoList.size) {
            gridInfoList.last()
        } else {
            gridInfoList[data.size - 1]
        }

        val modelImage = AppCompatResources.getDrawable(context, R.mipmap.ic_launcher)

        for (index in layoutInfo.indices) {
            val childView = LayoutInflater.from(context)
                .inflate(R.layout.activity_goods_page_preview_list_item, null)
            val childViewImage =
                childView.findViewById<ImageView>(R.id.GoodsPagePreviewListItemContentImageView)
            childViewImage.setImageDrawable(data[index])
            val gridLayoutParams = GridLayout.LayoutParams(
                GridLayout.spec(
                    layoutInfo[index].row,
                    layoutInfo[index].rowSpan,
                    layoutInfo[index].rowSpan.toFloat()
                ), GridLayout.spec(
                    layoutInfo[index].col,
                    layoutInfo[index].colSpan,
                    layoutInfo[index].colSpan.toFloat()
                )
            )
            gridLayoutParams.width = 0
            gridLayoutParams.height = GridLayout.LayoutParams.WRAP_CONTENT
            childView.viewTreeObserver.addOnPreDrawListener(object : OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    childView.viewTreeObserver.removeOnPreDrawListener(this)
                    val newLayoutParams = childView.layoutParams as GridLayout.LayoutParams
                    newLayoutParams.height = childView.width
                    childView.layoutParams = newLayoutParams
                    return true
                }
            })
            addView(childView, gridLayoutParams)
        }
    }
}
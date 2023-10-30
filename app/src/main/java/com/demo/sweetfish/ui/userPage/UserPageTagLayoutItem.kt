package com.demo.sweetfish.ui.userPage

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import com.example.sweetfish.R

class UserPageTagLayoutItem(context: Context, attrs: AttributeSet) :
    androidx.appcompat.widget.AppCompatTextView(context, attrs) {

    fun unSelect() {
        this.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.UserPageTagLayoutItemUnSelectBackground))
        this.setTextColor(resources.getColor(R.color.UserPageTagLayoutItemUnSelectText))
    }

    fun select() {
        this.backgroundTintList =
            ColorStateList.valueOf(resources.getColor(R.color.UserPageTagLayoutItemSelectBackground))
        this.setTextColor(resources.getColor(R.color.UserPageTagLayoutItemSelectText))
    }
}
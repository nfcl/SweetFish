package com.demo.sweetfish.ui.userPage

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.demo.sweetfish.SweetFishApplication
import com.example.sweetfish.R

class UserPageTagLayoutItem(context: Context, attrs: AttributeSet) :
    androidx.appcompat.widget.AppCompatTextView(context, attrs) {

    fun unSelect() {
        this.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                SweetFishApplication.context, R.color.UserPageTagLayoutItemUnSelectBackground
            )
        )
        this.setTextColor(
            ContextCompat.getColor(
                SweetFishApplication.context, R.color.UserPageTagLayoutItemUnSelectText
            )
        )
    }

    fun select() {
        this.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                SweetFishApplication.context, R.color.UserPageTagLayoutItemSelectBackground
            )
        )
        this.setTextColor(
            ContextCompat.getColor(
                SweetFishApplication.context, R.color.UserPageTagLayoutItemSelectText
            )
        )
    }
}
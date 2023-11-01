package com.demo.sweetfish.ui.component

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

abstract class TagLayoutItem(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    abstract fun select()

    abstract fun unSelect()

    abstract fun setText(text: String)

    abstract fun getText(): String

    abstract fun setOnClickListener(event: () -> Unit)

}
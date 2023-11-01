package com.demo.sweetfish.ui.searchPage

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.demo.sweetfish.ui.component.TagLayoutItem
import com.example.sweetfish.R

class SearchPageHistoryTagLayoutItem(context: Context, attrs: AttributeSet) :
    TagLayoutItem(context, attrs) {

    private val tagText: TextView by lazy { findViewById(R.id.SearchPageHistoryTagLayoutItemText) }

    override fun select() {
        //nothing
    }

    override fun unSelect() {
        //nothing
    }

    override fun setText(text: String) {
        tagText.text = text
    }

    override fun getText(): String {
        return tagText.text.toString()
    }

    override fun setOnClickListener(event: () -> Unit) {
        tagText.setOnClickListener { event() }
    }
}
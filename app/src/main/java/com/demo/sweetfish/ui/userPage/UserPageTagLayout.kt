package com.demo.sweetfish.ui.userPage

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.sweetfish.R
import com.google.android.material.internal.FlowLayout

@SuppressLint("RestrictedApi")
class UserPageTagLayout(context: Context, attrs: AttributeSet) : FlowLayout(context, attrs) {

    private val tagMap: MutableMap<String, UserPageTagLayoutItem> = mutableMapOf()

    fun init(tags: MutableMap<String, String>, event: (String) -> Unit) {
        tagMap.clear()
        this.removeAllViews()
        for ((key, value) in tags) {
            addTag(key, value, event)
        }
    }

    private fun addTag(key: String, value: String, event: (String) -> Unit) {
        val newItem: UserPageTagLayoutItem = LayoutInflater.from(context)
            .inflate(R.layout.activity_user_page_taglayoutitem, null) as UserPageTagLayoutItem
        newItem.setOnClickListener {
            select(key)
            event(key)
        }
        newItem.text = value
        this.addView(newItem)
        tagMap[key] = newItem
    }

    private fun select(key: String) {
        for ((tagKey, tagItem) in tagMap) {
            if (tagKey == key) {
                tagItem.select()
            } else {
                tagItem.unSelect()
            }
        }
    }

    fun forceSelectWithEvent(key: String) {
        tagMap[key]?.callOnClick()
    }
}
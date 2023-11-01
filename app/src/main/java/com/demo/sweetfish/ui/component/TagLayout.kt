package com.demo.sweetfish.ui.component

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import com.example.sweetfish.R
import com.google.android.material.internal.FlowLayout

@SuppressLint("RestrictedApi")
class TagLayout(context: Context, attrs: AttributeSet) : FlowLayout(context, attrs) {

    private val mItem: Int

    private val mTagMap: MutableMap<String, TagLayoutItem> = mutableMapOf()

    private var mOnClickEvent: (key: String) -> Unit = {}

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.TagLayout)
        mItem = ta.getResourceId(R.styleable.TagLayout_item, -1)
        ta.recycle()
    }

    fun init(tags: Map<String, String>, event: (String) -> Unit) {
        mOnClickEvent = event
        for ((key, value) in tags) {
            addTag(key, value)
        }
    }

    fun addTag(key: String, value: String) {
        if (mTagMap.containsKey(key)) {
            throw Exception("Multiple Key!")
        }
        val newItem = LayoutInflater.from(context)
            .inflate(mItem, null) as TagLayoutItem
        newItem.setOnClickListener {
            select(key)
            mOnClickEvent(key)
        }
        newItem.setText(value)
        this.addView(newItem)
        mTagMap[key] = newItem
    }

    fun removeTag(key: String) {
        val item = mTagMap.remove(key)
        if (item != null) {
            this.removeView(item)
        }
    }

    private fun select(key: String) {
        for ((tagKey, tagItem) in mTagMap) {
            if (tagKey == key) {
                tagItem.select()
            } else {
                tagItem.unSelect()
            }
        }
    }

    fun forceSelectWithEvent(key: String) {
        mTagMap[key]?.callOnClick()
    }

}
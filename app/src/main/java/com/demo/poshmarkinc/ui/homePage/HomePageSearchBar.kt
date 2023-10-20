package com.demo.poshmarkinc.ui.homePage

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.example.poshmarkinc.R

/**
 * 主页的底部导航栏控件
 * @property searchButton 搜索按钮
 * @property searchEditText 搜索框
 */
class HomePageSearchBar(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private var searchButton: Button
    private var searchEditText: EditText

    init {
        LayoutInflater.from(context).inflate(R.layout.activity_home_page_searchbar, this)
        searchButton = findViewById(R.id.SearchButton_Button)
        searchEditText = findViewById(R.id.SearchContent_EditText)
    }

    /**
     * 注册搜索按钮的点击监听器
     * @param event 调用时会将搜索框的内容作为参数传递
     */
    fun setSearchButtonEvent(event: (content: String) -> Unit) {
        searchButton.setOnClickListener {
            event(searchEditText.text.toString())
        }
    }
}
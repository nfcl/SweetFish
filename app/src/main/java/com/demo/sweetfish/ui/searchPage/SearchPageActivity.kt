package com.demo.sweetfish.ui.searchPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.demo.sweetfish.ui.component.TagLayout
import com.example.sweetfish.R

class SearchPageActivity : AppCompatActivity() {

    private val historyTagLayout: TagLayout by lazy { findViewById(R.id.SearchPageHistoryTagLayout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_page)
        initComponent()
        testHistory()
    }

    private fun initComponent() {
        val returnButton = findViewById<ImageView>(R.id.SearchPageReturnButton)
        returnButton.setOnClickListener { finish() }
    }

    private fun testHistory() {
        historyTagLayout.init(
            mapOf(
                "1" to "电动车",
                "2" to "双肩包",
                "3" to "早秋装搭配一整套",
                "4" to "手机壳",
                "5" to "华为mate60",
                "6" to "毛衣女",
                "7" to "裤子女",
                "8" to "牛仔裤",
                "9" to "半身裙",
                "10" to "大码女装",
            )
        ) {}
    }
}
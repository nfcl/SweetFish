package com.demo.sweetfish.ui.goodsPublishPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.sweetfish.R

class GoodsPublishPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_publish_page)
        findViewById<TextView>(R.id.GoodsPublishPageCancelButton).setOnClickListener {
            finish()
        }
    }
}
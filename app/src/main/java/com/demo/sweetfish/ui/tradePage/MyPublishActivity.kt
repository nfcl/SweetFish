package com.demo.sweetfish.ui.tradePage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.sweetfish.R

class MyPublishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page_userpageview_mypublish)
        findViewById<ImageView>(R.id.MyPublishReturnButton).setOnClickListener { finish() }
    }
}
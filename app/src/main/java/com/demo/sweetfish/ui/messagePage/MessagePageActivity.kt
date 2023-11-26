package com.demo.sweetfish.ui.messagePage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.sweetfish.R

class MessagePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_page)
        findViewById<ImageView>(R.id.MessageReturnButton).setOnClickListener { finish() }
    }
}

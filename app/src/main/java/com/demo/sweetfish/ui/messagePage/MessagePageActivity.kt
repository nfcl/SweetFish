package com.demo.sweetfish.ui.messagePage

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.sweetfish.R

class MessagePageActivity : AppCompatActivity() {

    companion object {
        fun startActivity(appCompatActivity: AppCompatActivity) {
            val intent = Intent(appCompatActivity, MessagePageActivity::class.java)
            appCompatActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_page)
        findViewById<ImageView>(R.id.MessageReturnButton).setOnClickListener { finish() }
    }
}

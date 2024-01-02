package com.demo.sweetfish.ui.chatPage

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.sweetfish.ui.messagePage.MessagePageActivity
import com.example.sweetfish.R

class ChatPageActivity : AppCompatActivity() {

    private val viewModel: ChatPageActivityViewModel by lazy {
        ViewModelProvider(
            this, ChatPageActivityViewModel.MessagePageActivityViewModelFactory()
        )[ChatPageActivityViewModel::class.java]
    }

    companion object {
        fun startActivity(appCompatActivity: AppCompatActivity) {
            val intent = Intent(appCompatActivity, ChatPageActivity::class.java)
            appCompatActivity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_page)
        findViewById<ImageView>(R.id.MessageReturnButton).setOnClickListener { finish() }
        initComponent()
    }

    private fun initComponent() {
        val messageList = findViewById<RecyclerView>(R.id.MessageList)
        messageList.layoutManager = LinearLayoutManager(this)
        messageList.adapter = ChatPageChatListAdapter(
            this, ArrayList()
        ) { chatId ->
            MessagePageActivity.startActivity(this, chatId)
        }
        viewModel.chatList.observe(this) { listData ->
            (messageList.adapter as ChatPageChatListAdapter).setListData(listData)
        }
    }
}
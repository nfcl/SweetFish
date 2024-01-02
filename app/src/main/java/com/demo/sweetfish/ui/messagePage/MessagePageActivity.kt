package com.demo.sweetfish.ui.messagePage

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.logic.repository.ImageSourceRepository
import com.demo.sweetfish.ui.goodsPage.GoodsPageActivity
import com.demo.sweetfish.ui.payPage.PayPageActivity
import com.example.sweetfish.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessagePageActivity : AppCompatActivity() {
    private val viewModel: MessagePageActivityViewModel by lazy {
        ViewModelProvider(
            this, MessagePageActivityViewModel.MessageChatPageActivityViewModelFactory()
        )[MessagePageActivityViewModel::class.java]
    }

    companion object {
        fun startActivity(appCompatActivity: AppCompatActivity, chatId: Long) {
            val newIntent = Intent(appCompatActivity, MessagePageActivity::class.java)
            newIntent.putExtra("chatId", chatId)
            appCompatActivity.startActivity(newIntent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_page)
        initComponent()
        val chatId = intent.getLongExtra("chatId", -1)
        if (chatId == -1L) {
            Toast.makeText(this, "chatId为空", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.setChatId(chatId)
        }
    }

    private fun initComponent() {

        val contentEdit: EditText = findViewById(R.id.editText)
        contentEdit.doAfterTextChanged { newTitle -> viewModel.editContent(newTitle.toString()) }

        val goodsList = findViewById<RecyclerView>(R.id.scrollView)
        goodsList.layoutManager = LinearLayoutManager(this)

        viewModel.chatInfo.observe(this) { chatInfo ->
            val goodsPreviewImage = findViewById<ImageView>(R.id.GoodsPreviewImage)
            ImageSourceRepository.findById(chatInfo.goodsPreviewImage).observe(this) {
                goodsPreviewImage.setImageDrawable(it?.content)
            }
            val goodsTitleTextView = findViewById<TextView>(R.id.goodsName)
            goodsTitleTextView.text = chatInfo.goodsTitle
            val goodsPriceTextView = findViewById<TextView>(R.id.price)
            goodsPriceTextView.text = chatInfo.goodsPrice.toString()
            val goodsPageButton = findViewById<LinearLayout>(R.id.MessagePageGoodsPageButton)
            goodsPageButton.setOnClickListener {
                GoodsPageActivity.startActivity(this, chatInfo.goodsId)
            }
            CoroutineScope(Dispatchers.IO).launch {
                val selfInfo: MessagePageActivityAdapter.MessageListCommonInfo
                val otherInfo: MessagePageActivityAdapter.MessageListCommonInfo
                if (chatInfo.buyerId == SweetFishApplication.loginUserId.value) {
                    selfInfo = MessagePageActivityAdapter.MessageListCommonInfo(
                        chatInfo.buyerId,
                        ImageSourceRepository.findByIdWithSuspend(chatInfo.buyerAvatar)?.content!!,
                        chatInfo.buyerName
                    )
                    otherInfo = MessagePageActivityAdapter.MessageListCommonInfo(
                        chatInfo.sellerId,
                        ImageSourceRepository.findByIdWithSuspend(chatInfo.sellerAvatar)?.content!!,
                        chatInfo.sellerName
                    )
                } else {
                    selfInfo = MessagePageActivityAdapter.MessageListCommonInfo(
                        chatInfo.sellerId,
                        ImageSourceRepository.findByIdWithSuspend(chatInfo.sellerAvatar)?.content!!,
                        chatInfo.sellerName
                    )
                    otherInfo = MessagePageActivityAdapter.MessageListCommonInfo(
                        chatInfo.buyerId,
                        ImageSourceRepository.findByIdWithSuspend(chatInfo.buyerAvatar)?.content!!,
                        chatInfo.buyerName
                    )
                }
                val adapter = MessagePageActivityAdapter(
                    listOf(), Pair(selfInfo, otherInfo)
                )
                runOnUiThread {
                    goodsList.adapter = adapter
                    obverseMessageList(goodsList, adapter)
                    findViewById<TextView>(R.id.MessagePagePayButton).setOnClickListener {
                        toPayPage(chatInfo.goodsId)
                    }
                }
            }
        }

        val publishButton = findViewById<TextView>(R.id.MessagePageMessagePublishButton)
        publishButton.setOnClickListener {
            viewModel.publishNewMessage()
        }
    }

    private fun toPayPage(goodsId: Long) {
        PayPageActivity.startActivity(this, goodsId)
    }

    private fun obverseMessageList(goodsList: RecyclerView, adapter: MessagePageActivityAdapter) {
        viewModel.messageList.observe(this) { messageList ->
            adapter.setListData(messageList)
            goodsList.scrollToPosition(adapter.itemCount - 1)
        }
    }
}
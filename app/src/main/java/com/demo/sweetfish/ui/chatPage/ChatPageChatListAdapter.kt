package com.demo.sweetfish.ui.chatPage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.demo.sweetfish.logic.model.MessageInfo
import com.demo.sweetfish.logic.repository.ImageSourceRepository
import com.example.sweetfish.R
import java.text.SimpleDateFormat
import java.util.Locale

class ChatPageChatListAdapter(
    private val appCompatActivity: AppCompatActivity,
    private var chatList: List<MessageInfo>,
    private var onChatClickEvent: (chatId: Long) -> Unit,
) : RecyclerView.Adapter<ChatPageChatListAdapter.ViewHolder>() {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val goodsPreviewPic: ImageView = view.findViewById(R.id.MessageGoodImage)
        val chatName: TextView = view.findViewById(R.id.MessageName)
        val chatMessage: TextView = view.findViewById(R.id.MessageContent)
        val chatAvatarPic: ImageView = view.findViewById(R.id.MessageImage)
        val chatTime: TextView = view.findViewById(R.id.MessageTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_chat_page_item, null, false)
        view.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chat = chatList[position]
        ImageSourceRepository.findById(chat.goodsPreviewPic).observe(appCompatActivity) {
            holder.goodsPreviewPic.setImageDrawable(it?.content)
        }
        ImageSourceRepository.findById(chat.chatAvatarPic).observe(appCompatActivity) {
            holder.chatAvatarPic.setImageDrawable(it?.content)
        }
        holder.chatName.text = chat.chatName
        holder.chatMessage.text = chat.chatMessage
        if (chat.chatTime != null) {
            holder.chatTime.text = dateFormat.format(chat.chatTime)
        }
        holder.itemView.findViewById<LinearLayout>(R.id.MessageInfoButton).setOnClickListener {
            onChatClickEvent(chat.chatId)
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    fun setListData(messageList: List<MessageInfo>) {
        notifyItemRangeRemoved(0, chatList.size)
        chatList = messageList
        notifyItemRangeChanged(0, chatList.size)
    }

}
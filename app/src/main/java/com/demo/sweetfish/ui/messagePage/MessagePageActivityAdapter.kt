package com.demo.sweetfish.ui.messagePage

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.demo.sweetfish.logic.model.Message
import com.example.sweetfish.R
import java.text.SimpleDateFormat
import java.util.Locale

class MessagePageActivityAdapter(
    private var mMessage: List<Message>,
    private var commonUserInfo: Pair<MessageListCommonInfo, MessageListCommonInfo>,
) : RecyclerView.Adapter<MessagePageActivityAdapter.ViewHolder>() {

    class MessageListCommonInfo(val userId: Long, val avatar: Drawable, val name: String)

    companion object {

        private const val SelfItemFlag = 0
        private const val OtherItemFlag = 1

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userPreviewPic: ImageView = view.findViewById(R.id.userAvatar)
        val chatMessage: TextView = view.findViewById(R.id.sendMessageContent)
        val chatTime: TextView = view.findViewById(R.id.sendTime)
    }

    override fun getItemViewType(position: Int): Int {
        return if (mMessage[position].senderId == commonUserInfo.first.userId) SelfItemFlag else OtherItemFlag
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when (viewType) {
            SelfItemFlag -> {
                return ViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.activity_message_page_other_item, parent, false)
                )
            }

            OtherItemFlag -> {
                return ViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.activity_message_page_self_item, parent, false)
                )
            }

            else -> {
                Log.d("Mine", "未知的消息类型")
                return ViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.activity_message_page_other_item, parent, false)
                )
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = mMessage[position]
        val userInfo: MessageListCommonInfo =
            if (message.senderId == commonUserInfo.first.userId) commonUserInfo.first else commonUserInfo.second
        holder.userPreviewPic.setImageDrawable(userInfo.avatar)
        holder.chatMessage.text = message.content
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
        val formattedTime = dateFormat.format(message.sendTime)
        holder.chatTime.text = formattedTime
    }

    override fun getItemCount(): Int {
        return mMessage.size
    }

    fun setListData(messageList: List<Message>) {
        notifyItemRangeRemoved(0, mMessage.size)
        mMessage = messageList
        notifyItemRangeChanged(0, mMessage.size)
    }

}
package com.demo.sweetfish.ui.messagePage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.demo.sweetfish.logic.model.GoodsWithSellerInfo
import com.demo.sweetfish.logic.model.MessageInfo
import com.demo.sweetfish.ui.homePage.HomePageGoodsListAdapter
import com.example.sweetfish.R
import java.text.SimpleDateFormat
import java.util.Locale


class MessagePageListAdapter (
    private var mMessageList: List<MessageInfo>,
    private var onMessageClickEvent: (message: MessageInfo) -> Unit,
):
    RecyclerView.Adapter<MessagePageListAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val goodsPreviewPic: ImageView = view.findViewById(R.id.MessageGoodImage)
        val chatName: TextView = view.findViewById(R.id.MessageName)
        val chatMessage: TextView = view.findViewById(R.id.MessageContent)
        val chatAvatarPic: ImageView = view.findViewById(R.id.MessageImage)
        val chatTime: TextView = view.findViewById(R.id.MessageTime)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_message_page_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = mMessageList[position]
        holder.goodsPreviewPic.setImageDrawable(message.goodsPreviewPic)
        holder.chatName.text = message.chatName
        holder.chatMessage.text = message.chatMessage
        holder.chatAvatarPic.setImageDrawable(message.chatAvatarPic)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
        val formattedTime = dateFormat.format(message.chatTime)
        // 设置格式化后的时间到 TextView 中
        holder.chatTime.text = formattedTime
        holder.itemView.findViewById<LinearLayout>(R.id.MessageInfoButton)
            .setOnClickListener {
                onMessageClickEvent(message)
            }
    }


    override fun getItemCount(): Int {
        return mMessageList.size
    }
    fun setListData(messageList: List<MessageInfo>) {
        mMessageList = messageList
        notifyItemRangeChanged(0, mMessageList.size)
    }

}
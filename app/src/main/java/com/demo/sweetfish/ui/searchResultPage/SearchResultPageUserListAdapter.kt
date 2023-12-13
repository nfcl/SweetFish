package com.demo.sweetfish.ui.searchResultPage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.demo.sweetfish.logic.model.SearchResultPageUserInfo
import com.demo.sweetfish.logic.repository.ImageSourceRepository
import com.demo.sweetfish.ui.component.RoundImageView
import com.example.sweetfish.R

class SearchResultPageUserListAdapter(
    private val appCompatActivity: AppCompatActivity,
    private var userList: List<SearchResultPageUserInfo>,
    private val onClickUserEvent: (Long) -> Unit,
) : Adapter<SearchResultPageUserListAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userNameTextView: TextView =
            view.findViewById(R.id.SearchResultPageUserListItemUserNameTextView)
        val userIdTextView: TextView =
            view.findViewById(R.id.SearchResultPageUserListItemUserIdTextView)
        val userFanNumTextView: TextView =
            view.findViewById(R.id.SearchResultPageUserListItemUserFanNumTextView)
        val userAvatarImageView: RoundImageView =
            view.findViewById(R.id.SearchResultPageUserListItemUserAvatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.activity_search_result_page_userlist_view_userlist_item, parent, false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = userList[position]
        holder.itemView.setOnClickListener { onClickUserEvent(info.userId) }
        ImageSourceRepository.findById(info.userAvatar).observe(appCompatActivity) {
            holder.userAvatarImageView.setImageDrawable(it?.content)
        }
        holder.userNameTextView.text = info.userName
        holder.userIdTextView.text = "Id : ${info.userId}"
        holder.userFanNumTextView.text = "粉丝 : ${info.userFanNum}"
    }

    fun setListData(newUserList: List<SearchResultPageUserInfo>) {
        userList = newUserList
        notifyItemRangeChanged(0, userList.size)
    }

}
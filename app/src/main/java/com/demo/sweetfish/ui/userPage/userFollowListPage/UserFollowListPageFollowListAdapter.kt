package com.demo.sweetfish.ui.userPage.userFollowListPage

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.logic.model.UserWithFollowInfo
import com.demo.sweetfish.logic.repository.ImageSourceRepository
import com.demo.sweetfish.ui.component.RoundImageView
import com.example.sweetfish.R

class UserFollowListPageFollowListAdapter(
    private val appCompatActivity: AppCompatActivity,
    private var listData: List<UserWithFollowInfo>,
    val clickUserEvent: (Long) -> Unit,
    val clickFollowEvent: (UserWithFollowInfo) -> Unit,
    private val followedText: String,
    private val unFollowedText: String,
    private val followedBackgroundTint: ColorStateList,
    private val unFollowedBackgroundTint: ColorStateList,
    private val followedTextColor: ColorStateList,
    private val unFollowedTextColor: ColorStateList,
) : RecyclerView.Adapter<UserFollowListPageFollowListAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userNameTextView: TextView =
            view.findViewById(R.id.UserFollowListPageFollowListItemUserNameTextView)
        val userDescribeTextView: TextView =
            view.findViewById(R.id.UserFollowListPageFollowListItemUserDescribeTextView)
        val userFanNumTextView: TextView =
            view.findViewById(R.id.UserFollowListPageFollowListItemUserFanNumTextView)
        val userAvatarImageView: RoundImageView =
            view.findViewById(R.id.UserFollowListPageFollowListItemUserAvatarImageView)
        val followButton: TextView =
            view.findViewById(R.id.UserFollowListPageFollowListItemFollowButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_user_follow_list_page_follow_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = listData[position]
        holder.userNameTextView.text = info.userName ?: info.userId.toString()
        holder.userDescribeTextView.text = info.userDescribe ?: "这个用户很神秘哦，什么都没留下"
        holder.userFanNumTextView.text = "粉丝数 ${info.userFanNum}"
        ImageSourceRepository.findById(info.userAvatar).observe(appCompatActivity) {
            holder.userAvatarImageView.setImageDrawable(it?.content)
        }
        if (info.userId == SweetFishApplication.loginUserId.value!!) {
            holder.followButton.alpha = 0f
        } else if (info.isFollowed) {
            holder.followButton.text = followedText
            holder.followButton.backgroundTintList = followedBackgroundTint
            holder.followButton.setTextColor(followedTextColor)
        } else {
            holder.followButton.text = unFollowedText
            holder.followButton.backgroundTintList = unFollowedBackgroundTint
            holder.followButton.setTextColor(unFollowedTextColor)
        }
        holder.followButton.setOnClickListener {
            clickFollowEvent(info)
        }
        holder.itemView.setOnClickListener { clickUserEvent(info.userId) }
    }

    fun setListData(newData: List<UserWithFollowInfo>) {
        notifyItemRangeRemoved(0, listData.size)
        listData = newData
        notifyItemRangeInserted(0, listData.size)
    }
}
package com.demo.sweetfish.ui.userPage.userFanListPage

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.ui.userPage.others.OthersUserPageActivity
import com.demo.sweetfish.ui.userPage.personal.PersonalUserPageActivity
import com.demo.sweetfish.ui.userPage.userFollowListPage.UserFollowListPageFollowListAdapter
import com.example.sweetfish.R
import kotlin.concurrent.thread

class UserFanListPageActivity : AppCompatActivity() {

    companion object {
        fun startActivity(appCompatActivity: AppCompatActivity, userId: Long) {
            val intent = Intent(appCompatActivity, UserFanListPageActivity::class.java)
            intent.putExtra("UserId", userId)
            appCompatActivity.startActivity(intent)
        }
    }

    private val viewModel: UserFanListPageActivityViewModel by lazy {
        ViewModelProvider(
            this, UserFanListPageActivityViewModel.UserFanListPageActivityViewModelFactory()
        )[UserFanListPageActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_fan_list_page)
        initComponent()
        viewModel.setUserId(intent.getLongExtra("UserId", -1))
    }

    private fun initComponent() {
        val returnButton = findViewById<ImageView>(R.id.UserFanListPageReturnButton)
        returnButton.setOnClickListener { finish() }
        val followList = findViewById<RecyclerView>(R.id.UserFanListPageFanListRecyclerView)
        followList.layoutManager = LinearLayoutManager(this)
        followList.adapter = UserFollowListPageFollowListAdapter(
            this,
            listOf(),
            { id ->
                if (id == SweetFishApplication.loginUserId.value!!) {
                    PersonalUserPageActivity.startActivity(this)
                } else {
                    OthersUserPageActivity.startActivity(this, id)
                }
            },
            { info ->
                thread {
                    if (info.isFollowed) {
                        viewModel.unFollowUser(info.userId)
                    } else {
                        viewModel.followUser(info.userId)
                    }
                }
            },
            getString(R.string.OthersUserPageFollowButton_Followed_Text),
            getString(R.string.OthersUserPageFollowButton_UnFollowed_Text),
            ColorStateList.valueOf(getColor(R.color.OthersUserPageFollowButton_Followed_BackgroundTint)),
            ColorStateList.valueOf(getColor(R.color.OthersUserPageFollowButton_UnFollowed_BackgroundTint)),
            ColorStateList.valueOf(getColor(R.color.OthersUserPageFollowButton_Followed_TextColor)),
            ColorStateList.valueOf(getColor(R.color.OthersUserPageFollowButton_UnFollowed_TextColor))
        )
        viewModel.fanList.observe(this) { data ->
            (followList.adapter as UserFollowListPageFollowListAdapter).setListData(data)
        }
    }
}
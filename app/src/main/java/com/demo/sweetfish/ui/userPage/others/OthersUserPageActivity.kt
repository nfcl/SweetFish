package com.demo.sweetfish.ui.userPage.others

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.demo.sweetfish.ui.component.RoundImageView
import com.demo.sweetfish.ui.goodsPage.GoodsPageActivity
import com.demo.sweetfish.ui.homePage.HomePageGoodsListAdapter
import com.demo.sweetfish.ui.userPage.userFanListPage.UserFanListPageActivity
import com.demo.sweetfish.ui.userPage.userFollowListPage.UserFollowListPageActivity
import com.example.sweetfish.R
import kotlin.concurrent.thread

class OthersUserPageActivity : AppCompatActivity() {

    companion object {

        fun startActivity(appCompatActivity: AppCompatActivity, userId: Long) {
            val intent = Intent(appCompatActivity, OthersUserPageActivity::class.java)
            intent.putExtra("UserId", userId)
            appCompatActivity.startActivity(intent)
        }

    }

    private val viewModel: OthersUserPageActivityViewModel by lazy {
        ViewModelProvider(
            this, OthersUserPageActivityViewModel.OthersUserPageActivityViewModelFactory()
        )[OthersUserPageActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_others_user_page)
        val userId = intent.getLongExtra("UserId", -1)
        thread {
            viewModel.setUserId(userId)
        }
        initViewPager()
        initNavigationBar()
        initComponent()
    }

    private fun initComponent() {
        val userAvatarImageViewMain =
            findViewById<RoundImageView>(R.id.UserPageUserAvatarImageViewMain)
        val userAvatarImageViewTopNavigation =
            findViewById<RoundImageView>(R.id.UserPageUserAvatarImageViewTopNavigation)
        val userBackgroundImageView = findViewById<ImageView>(R.id.UserPageUserBackgroundImageView)
        val userNameTextViewMain = findViewById<TextView>(R.id.UserPageUserNameTextViewMain)
        val userNameTextViewTopNavigation =
            findViewById<TextView>(R.id.UserPageUserNameTextViewTopNavigation)
        val userIdTextView = findViewById<TextView>(R.id.UserPageUserIdTextView)
        val userDescribeTextView = findViewById<TextView>(R.id.UserPageUserDescribeTextView)
        val userSexTextView = findViewById<TextView>(R.id.UserPageUserSexTextView)
        val userFollowNumTextView = findViewById<TextView>(R.id.UserPageUserFollowNum)
        val userFanNumTextView = findViewById<TextView>(R.id.UserPageUserFanNum)
        viewModel.userAvatar.observe(this) { avatar ->
            userAvatarImageViewMain.setImageDrawable(avatar.content)
            userAvatarImageViewTopNavigation.setImageDrawable(avatar.content)
        }
        viewModel.userName.observe(this) { name ->
            userNameTextViewMain.text = name
            userNameTextViewTopNavigation.text = name
        }
        viewModel.userBackground.observe(this) { background ->
            userBackgroundImageView.setImageDrawable(
                background.content
            )
        }
        viewModel.userId.observe(this) { id -> userIdTextView.text = "ID $id" }
        viewModel.userDescribe.observe(this) { describe ->
            userDescribeTextView.text = describe ?: "这家伙很神秘，没有写个人简介"
        }
        viewModel.userSex.observe(this) { sex ->
            userSexTextView.text = when (sex) {
                null -> "秘密"
                true -> "男生"
                false -> "女生"
            }
        }
        viewModel.userFollowNum.observe(this) { followNum ->
            userFollowNumTextView.text = followNum.toString()
        }
        viewModel.userFanNum.observe(this) { fanNum ->
            userFanNumTextView.text = fanNum.toString()
        }
        fun initFollowButton() {
            val userFollowButtonMain = findViewById<TextView>(R.id.OthersUserPageFollowButtonMain)
            val userFollowButtonTopNavigation =
                findViewById<TextView>(R.id.OthersUserPageFollowButtonTopNavigation)
            val followedText = getString(R.string.OthersUserPageFollowButton_Followed_Text)
            val unFollowedText = getString(R.string.OthersUserPageFollowButton_UnFollowed_Text)
            val followedBackgroundTint =
                ColorStateList.valueOf(getColor(R.color.OthersUserPageFollowButton_Followed_BackgroundTint))
            val unFollowedBackgroundTint =
                ColorStateList.valueOf(getColor(R.color.OthersUserPageFollowButton_UnFollowed_BackgroundTint))
            val followedTextColor =
                ColorStateList.valueOf(getColor(R.color.OthersUserPageFollowButton_Followed_TextColor))
            val unFollowedTextColor =
                ColorStateList.valueOf(getColor(R.color.OthersUserPageFollowButton_UnFollowed_TextColor))
            viewModel.isFollowed.observe(this) { isFollowed ->
                if (isFollowed) {
                    userFollowButtonMain.text = followedText
                    userFollowButtonTopNavigation.text = followedText
                    userFollowButtonMain.backgroundTintList = followedBackgroundTint
                    userFollowButtonTopNavigation.backgroundTintList = followedBackgroundTint
                    userFollowButtonMain.setTextColor(followedTextColor)
                    userFollowButtonTopNavigation.setTextColor(followedTextColor)
                } else {
                    userFollowButtonMain.text = unFollowedText
                    userFollowButtonTopNavigation.text = unFollowedText
                    userFollowButtonMain.backgroundTintList = unFollowedBackgroundTint
                    userFollowButtonTopNavigation.backgroundTintList = unFollowedBackgroundTint
                    userFollowButtonMain.setTextColor(unFollowedTextColor)
                    userFollowButtonTopNavigation.setTextColor(unFollowedTextColor)
                }
            }
            userFollowButtonMain.setOnClickListener { clickFollowButton() }
            userFollowButtonTopNavigation.setOnClickListener { clickFollowButton() }
        }
        initFollowButton()
        val followListButton = findViewById<LinearLayout>(R.id.OthersUserPageFollowListButton)
        followListButton.setOnClickListener {
            UserFollowListPageActivity.startActivity(this, viewModel.userId.value!!)
        }
        val fanListButton = findViewById<LinearLayout>(R.id.OthersUserPageFanListButton)
        fanListButton.setOnClickListener {
            UserFanListPageActivity.startActivity(this, viewModel.userId.value!!)
        }
    }

    private fun clickFollowButton() {
        thread {
            if (viewModel.isFollowed.value!!) {
                viewModel.unFollowUser()
            } else {
                viewModel.followUser()
            }
        }
    }

    private fun initViewPager() {
        val goodsList = findViewById<RecyclerView>(R.id.UserPageGoodsList)
        goodsList.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        val adapter = HomePageGoodsListAdapter(this, ArrayList(), { goodsId ->
            GoodsPageActivity.startActivity(this, goodsId)
        }, {})
        goodsList.adapter = adapter
        viewModel.goodsList.observe(this) { data ->
            adapter.setListData(data)
        }
    }

    private fun initNavigationBar() {
        val navigation = findViewById<LinearLayout>(R.id.UserPageTopNavigationBar)
        val scrollView = findViewById<ScrollView>(R.id.UserPageScrollView)
        scrollView.setOnScrollChangeListener { _, _, newY, _, _ ->
            val percent: Float = when {
                newY < 200 -> 0f
                newY > 400 -> 1f
                else -> (newY - 200) / 200.0f
            }
            navigation.alpha = percent
        }
        navigation.alpha = 0f
        navigation.findViewById<ImageView>(R.id.UserPageNavigationBarReturnButton)
            .setOnClickListener {
                finish()
            }
    }
}
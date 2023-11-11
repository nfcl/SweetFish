package com.demo.sweetfish.ui.userPage.others

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.ui.component.RoundImageView
import com.demo.sweetfish.ui.component.WithInitEventViewPager2Adapter
import com.demo.sweetfish.ui.userPage.UserPageTagLayout
import com.example.sweetfish.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
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
            userAvatarImageViewMain.setImageDrawable(avatar)
            userAvatarImageViewTopNavigation.setImageDrawable(avatar)
        }
        viewModel.userName.observe(this) { name ->
            userNameTextViewMain.text = name
            userNameTextViewTopNavigation.text = name
        }
        viewModel.userBackground.observe(this) { background ->
            userBackgroundImageView.setImageDrawable(
                background
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
                if (isFollowed == 1) {
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
    }

    private fun clickFollowButton() {
        thread {
            if (viewModel.isFollowed.value!! == 1) {
                viewModel.unFollowUser()
            } else {
                viewModel.followUser()
            }
        }
    }

    private fun initViewPager() {
        val viewPager: ViewPager2 = findViewById(R.id.activity_user_page_user_relations_viewpage)
        val tabLayout: TabLayout =
            findViewById(R.id.activity_user_page_user_relations_viewpage_tablayout)
        val views: List<Pair<View, (View) -> Unit>> = listOf(
            Pair(
                LayoutInflater.from(SweetFishApplication.context).inflate(
                    R.layout.activity_personal_user_page_userrelations_viewpage_goodsview, null
                ), ::initRelationGoodsView
            ), Pair(
                LayoutInflater.from(SweetFishApplication.context).inflate(
                    R.layout.activity_personal_user_page_userrelations_viewpage_appriseview, null
                ), ::initRelationAppraiseView
            )
        )
        viewPager.adapter = WithInitEventViewPager2Adapter(views)
        val tabTextList: List<String> = listOf("宝贝", "评价")
        TabLayoutMediator(
            tabLayout, viewPager
        ) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()
    }

    private fun initRelationGoodsView(viewItem: View) {
        val tagLayout = viewItem.findViewById<UserPageTagLayout>(R.id.UserPageGoodsTagLayout)
        tagLayout.init(mutableMapOf("All" to "全部", "InSell" to "在卖", "SoldOut" to "卖光")) {

        }
        tagLayout.forceSelectWithEvent("All")
    }

    private fun initRelationAppraiseView(viewItem: View) {
        val tagLayout = viewItem.findViewById<UserPageTagLayout>(R.id.UserPageAppriseTagLayout)
        tagLayout.init(mutableMapOf()) {

        }
//        tagLayout.forceSelectWithEvent("")
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
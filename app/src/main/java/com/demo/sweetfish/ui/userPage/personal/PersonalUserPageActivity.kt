package com.demo.sweetfish.ui.userPage.personal

import android.content.Intent
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
import com.demo.sweetfish.ui.userPage.userFanListPage.UserFanListPageActivity
import com.demo.sweetfish.ui.userPage.userFollowListPage.UserFollowListPageActivity
import com.demo.sweetfish.ui.userPage.userInfoEdit.UserInfoEditPageActivity
import com.example.sweetfish.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class PersonalUserPageActivity : AppCompatActivity() {

    companion object {

        fun startActivity(appCompatActivity: AppCompatActivity) {
            val intent = Intent(appCompatActivity, PersonalUserPageActivity::class.java)
            appCompatActivity.startActivity(intent)
        }

    }

    private val viewModel: PersonalUserPageActivityViewModel by lazy {
        ViewModelProvider(
            this, PersonalUserPageActivityViewModel.PersonalUserPageActivityViewModelFactory()
        )[PersonalUserPageActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_user_page)
        initViewPager()
        initNavigationBar()
        initComponent()
    }

    private fun initComponent() {
        findViewById<TextView>(R.id.PersonalUserPageEditInfoButton1).setOnClickListener { toEditInfoPage() }
        findViewById<TextView>(R.id.PersonalUserPageEditInfoButton2).setOnClickListener { toEditInfoPage() }
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
        val followListButton = findViewById<LinearLayout>(R.id.PersonalUserPageFollowListButton)
        followListButton.setOnClickListener {
            UserFollowListPageActivity.startActivity(this, viewModel.userId.value!!)
        }
        val fanListButton = findViewById<LinearLayout>(R.id.PersonalUserPageFanListButton)
        fanListButton.setOnClickListener {
            UserFanListPageActivity.startActivity(this, viewModel.userId.value!!)
        }
    }

    private fun toEditInfoPage() {
        val newIntent = Intent(this, UserInfoEditPageActivity::class.java)
        startActivity(newIntent)
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
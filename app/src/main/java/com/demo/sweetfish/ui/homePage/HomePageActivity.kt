package com.demo.sweetfish.ui.homePage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.ui.component.RoundImageView
import com.demo.sweetfish.ui.goodsPublishPage.GoodsPublishPageActivity
import com.demo.sweetfish.ui.messagePage.MessagePageActivity
import com.demo.sweetfish.ui.searchPage.SearchPageActivity
import com.demo.sweetfish.ui.tradePage.MyBoughtActivity
import com.demo.sweetfish.ui.tradePage.MyPublishActivity
import com.demo.sweetfish.ui.tradePage.MySoldActivity
import com.demo.sweetfish.ui.userPage.others.OthersUserPageActivity
import com.demo.sweetfish.ui.userPage.personal.PersonalUserPageActivity
import com.demo.sweetfish.ui.userPage.userFanListPage.UserFanListPageActivity
import com.demo.sweetfish.ui.userPage.userFollowListPage.UserFollowListPageActivity
import com.example.sweetfish.R

class HomePageActivity : AppCompatActivity() {

    private val viewModel: HomePageActivityViewModel by lazy {
        ViewModelProvider(
            this, HomePageActivityViewModel.HomePageActivityViewModelFactory()
        )[HomePageActivityViewModel::class.java]
    }

    companion object {
        const val HOME_PAGE: Int = 0
        const val USER_PAGE: Int = 1
    }

    private lateinit var viewPager: NoScrollViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        initViewPager()
        initNavigation()
        viewModel.refreshGoodsList()
    }

    private fun initNavigation() {
        val navigationBar: HomePageNavigationBar = findViewById(R.id.HomePageNavigationBar)
        navigationBar.setHomePageButtonOnClickListener {
            viewPager.setCurrentItem(HOME_PAGE, false)
        }
        navigationBar.setUserPageButtonOnClickListener {
            viewPager.setCurrentItem(USER_PAGE, false)
        }
        navigationBar.setGoodsPublishPageButtonOnClickListener {
            startActivity(Intent(this, GoodsPublishPageActivity::class.java))
        }
    }

    private fun initViewPager() {
        val viewList: ArrayList<Pair<View, (View) -> Unit>> = arrayListOf(
            Pair(
                layoutInflater.inflate(R.layout.activity_home_page_homepageview, null, false),
                ::initHomePage
            ), Pair(
                layoutInflater.inflate(R.layout.activity_home_page_userpageview, null, false),
                ::initUserPage
            )
        )
        viewPager = findViewById(R.id.ContentViewPager)
        viewPager.adapter = HomePageViewPagerAdapter(viewList)
    }

    private fun initHomePage(homePageView: View) {

        fun initGoodsList() {
            val goodsList: RecyclerView = homePageView.findViewById(R.id.HomePageGoodsList)
            goodsList.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            goodsList.adapter = HomePageGoodsListAdapter(ArrayList(), {}, { userId ->
                if (userId == SweetFishApplication.loginUserId.value!!) {
                    PersonalUserPageActivity.startActivity(this)
                } else {
                    OthersUserPageActivity.startActivity(this, userId)
                }
            })
            viewModel.goodsList.observe(this) { listData ->
                (goodsList.adapter as HomePageGoodsListAdapter).setListData(listData)
            }
        }

        fun initSearchBar() {
            val searchBar: LinearLayout = findViewById(R.id.HomePageSearchBar)
            searchBar.setOnClickListener {
                startActivity(
                    Intent(
                        this, SearchPageActivity::class.java
                    )
                )
            }
            val messageButton: LinearLayout = findViewById(R.id.HomePageMessageButton)
            messageButton.setOnClickListener { MessagePageActivity.startActivity(this) }
        }
        initGoodsList()
        initSearchBar()
    }

    private fun initUserPage(userPageView: View) {
        val userNameTextView =
            userPageView.findViewById<TextView>(R.id.HomePageUserPageViewUserNameText)
        val userAvatarImageView =
            userPageView.findViewById<RoundImageView>(R.id.HomePageUserPageViewUserAvatarImageView)
        userNameTextView.setOnClickListener {
            startActivity(Intent(this, PersonalUserPageActivity::class.java))
        }
        userPageView.findViewById<LinearLayout>(R.id.HomePageUserPageViewMyPublishButton)
            .setOnClickListener {
                startActivity(Intent(this, MyPublishActivity::class.java))
            }
        userPageView.findViewById<LinearLayout>(R.id.HomePageUserPageViewMySoldButton)
            .setOnClickListener {
                startActivity(Intent(this, MySoldActivity::class.java))
            }
        userPageView.findViewById<LinearLayout>(R.id.HomePageUserPageViewMyBoughtButton)
            .setOnClickListener {
                startActivity(Intent(this, MyBoughtActivity::class.java))
            }
        viewModel.userName.observe(this) { name -> userNameTextView.text = name }
        viewModel.userAvatar.observe(this) { avatar -> userAvatarImageView.setImageDrawable(avatar) }
        val userFollowNumTextView =
            findViewById<TextView>(R.id.HomePageUserPageViewFollowNumTextView)
        val userFanNumTextView = findViewById<TextView>(R.id.HomePageUserPageViewFanNumTextView)
        viewModel.userFollowNum.observe(this) { followNum ->
            userFollowNumTextView.text = followNum.toString()
        }
        viewModel.userFanNum.observe(this) { fanNum ->
            userFanNumTextView.text = fanNum.toString()
        }
        val userFollowListButton =
            findViewById<LinearLayout>(R.id.HomePageUserPageViewFollowListButton)
        userFollowListButton.setOnClickListener {
            UserFollowListPageActivity.startActivity(
                this, SweetFishApplication.loginUserId.value!!
            )
        }
        val userFanListButton = findViewById<LinearLayout>(R.id.HomePageUserPageViewFanListButton)
        userFanListButton.setOnClickListener {
            UserFanListPageActivity.startActivity(
                this, SweetFishApplication.loginUserId.value!!
            )
        }
    }
}
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
import com.demo.sweetfish.AppDatabase
import com.demo.sweetfish.logic.model.Goods
import com.demo.sweetfish.logic.model.GoodsPreviewImage
import com.demo.sweetfish.logic.model.User
import com.demo.sweetfish.ui.goodsPublishPage.GoodsPublishPageActivity
import com.demo.sweetfish.ui.searchPage.SearchPageActivity
import com.demo.sweetfish.ui.tradePage.MyBoughtActivity
import com.demo.sweetfish.ui.tradePage.MyPublishActivity
import com.demo.sweetfish.ui.tradePage.MySoldActivity
import com.demo.sweetfish.ui.userPage.personal.PersonalUserPageActivity
import com.example.sweetfish.R
import utils.DrawableUtils
import utils.DrawableUtils.Companion.toBytes
import java.time.Instant
import kotlin.concurrent.thread
import kotlin.math.abs
import kotlin.random.Random

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
        listTest()
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
            goodsList.adapter = HomePageGoodeListAdapter(ArrayList())
            viewModel.goodsList.observe(this) { listData ->
                goodsList.adapter = HomePageGoodeListAdapter(listData)
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
        }
        initGoodsList()
        initSearchBar()
    }

    private fun initUserPage(userPageView: View) {
        userPageView.findViewById<TextView>(R.id.HomePageUserPageViewUserNameText)
            .setOnClickListener {
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
    }

    /**
     * 测试用数据生成
     */
    private fun listTest() {
        fun userInit(num: Int): List<User> {
            val result = ArrayList<User>()
            val random = Random(Instant.now().epochSecond)
            for (i in 1..num) {
                result.add(
                    User(
                        "用户$i",
                        random.nextBoolean(),
                        "123456",
                        "123456",
                        DrawableUtils.getGradientDrawable(
                            100, 100, intArrayOf(
                                random.nextInt() % 0xFFFFFF, random.nextInt() % 0xFFFFFF
                            )
                        ).toBytes()
                    )
                )
            }
            return result
        }

        fun goodsInit(num: Int, users: List<User>): List<Goods> {
            val result = ArrayList<Goods>()
            val random = Random(Instant.now().epochSecond)
            for (i in 1..num) {
                result.add(
                    Goods(
                        "商品名称$i",
                        (((random.nextDouble() * 100).toInt()) % 1000).toDouble() / 100,
                        users[abs(random.nextInt()) % users.size].id
                    )
                )
            }
            return result
        }

        fun goodsPreviewImageInit(goods: List<Goods>): List<GoodsPreviewImage> {
            val result = ArrayList<GoodsPreviewImage>()
            val random = Random(Instant.now().epochSecond)
            for (good in goods) {
                result.add(
                    GoodsPreviewImage(
                        good.id,
                        DrawableUtils.getGradientDrawable(
                            abs(random.nextInt()) % 100 + 500,
                            abs(random.nextInt()) % 100 + 500,
                            intArrayOf(
                                random.nextInt() % 0xFFFFFF, random.nextInt() % 0xFFFFFF
                            )
                        ).toBytes()
                    )
                )
            }
            return result
        }

        thread {
            val userDao = AppDatabase.getDatabase().userDao()
            val goodsDao = AppDatabase.getDatabase().goodsDao()
            val goodsPreviewImageDao = AppDatabase.getDatabase().goodsPreviewImageDao()

            userDao.deleteAll()
            goodsDao.deleteAll()

            val userList = userInit(10)

            for (data in userList) {
                data.id = userDao.insert(data)
            }

            val goodsList = goodsInit(20, userList)

            for (data in goodsList) {
                data.id = goodsDao.insert(data)
            }

            val goodsPreviewImageList = goodsPreviewImageInit(goodsList)

            for (data in goodsPreviewImageList) {
                data.id = goodsPreviewImageDao.insert(data)
            }
            viewModel.refreshGoodsList()
        }
    }
}
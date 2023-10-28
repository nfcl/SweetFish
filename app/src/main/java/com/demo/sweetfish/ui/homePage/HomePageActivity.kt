package com.demo.sweetfish.ui.homePage

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.demo.sweetfish.AppDatabase
import com.demo.sweetfish.logic.model.Goods
import com.demo.sweetfish.logic.model.User
import com.example.sweetfish.R
import org.jetbrains.annotations.TestOnly
import utils.DrawableUtils
import utils.DrawableUtils.Companion.toBytes
import java.time.Instant
import kotlin.concurrent.thread
import kotlin.math.abs
import kotlin.random.Random

class HomePageActivity : AppCompatActivity() {

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
    }

    private fun initNavigation() {
        val navigationBar: HomePageNavigationBar = findViewById(R.id.HomePageNavigationBar)
        navigationBar.setHomePageButtonOnClickListener {
            viewPager.setCurrentItem(HOME_PAGE, false)
        }
        navigationBar.setUserPageButtonOnClickListener {
            viewPager.setCurrentItem(USER_PAGE, false)
        }
    }

    private fun initViewPager() {
        val viewList: ArrayList<Pair<View, (View) -> Unit>> = arrayListOf(
            Pair(
                layoutInflater.inflate(R.layout.activity_home_page_homepageview, null, false),
                ::initHomePage
            ),
            Pair(
                layoutInflater.inflate(R.layout.activity_home_page_userpageview, null, false),
                ::initUserPage
            )
        )
        viewPager = findViewById(R.id.ContentViewPager)
        viewPager.adapter = HomePageViewPagerAdapter(viewList)
    }

    private fun initHomePage(homePageView: View) {
        fun initGoodsList() {
            val goodsList: HomePageGoodsList = homePageView.findViewById(R.id.HomePageGoodsList)
            thread {
                goodsList.setGoodsList(
                    AppDatabase.getDatabase().goodsWithSellerInfoDao().findAll()
                )
            }
        }
        initGoodsList()
    }

    private fun initUserPage(userPageView: View) {

    }

    /**
     * 测试用数据生成
     */
    @TestOnly
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
                        users[abs(random.nextInt()) % users.size].id,
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
        }
    }
}
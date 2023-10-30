package com.demo.sweetfish.ui.homePage

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.demo.sweetfish.AppDatabase
import com.demo.sweetfish.logic.model.Goods
import com.demo.sweetfish.logic.model.GoodsWithSellerInfo
import com.demo.sweetfish.logic.model.User
import com.demo.sweetfish.ui.goodsPublishPage.GoodsPublishPageActivity
import com.demo.sweetfish.ui.tradePage.MyBoughtActivity
import com.demo.sweetfish.ui.tradePage.MyPublishActivity
import com.demo.sweetfish.ui.tradePage.MySoldActivity
import com.demo.sweetfish.ui.userPage.personal.PersonalUserPageActivity
import com.example.sweetfish.R
import org.jetbrains.annotations.TestOnly
import utils.DrawableUtils
import utils.DrawableUtils.Companion.toBytes
import java.lang.ref.WeakReference
import java.time.Instant
import kotlin.concurrent.thread
import kotlin.math.abs
import kotlin.random.Random

class HomePageActivity : AppCompatActivity() {

    companion object {
        const val HOME_PAGE: Int = 0
        const val USER_PAGE: Int = 1
        const val GOODSLISTINIT: Int = 0x0001
    }

    private lateinit var viewPager: NoScrollViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        initViewPager()
        initNavigation()
    }

    private val weakHandler by lazy { WeakReferenceHandler(this) }

    class WeakReferenceHandler(obj: HomePageActivity) : Handler(Looper.getMainLooper()) {
        private val mRef: WeakReference<HomePageActivity> = WeakReference(obj)

        override fun handleMessage(msg: Message) {
            mRef.get()?.run {
                when (msg.what) {
                    GOODSLISTINIT -> {
                        val goodsList: HomePageGoodsList =
                            homePageView.findViewById(R.id.HomePageGoodsList)
                        val goodsData = msg.obj as List<GoodsWithSellerInfo>
                        goodsList.setGoodsList(
                            goodsData
                        )
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        //退出页面时，置空所以的Message
        weakHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
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
            ),
            Pair(
                layoutInflater.inflate(R.layout.activity_home_page_userpageview, null, false),
                ::initUserPage
            )
        )
        viewPager = findViewById(R.id.ContentViewPager)
        viewPager.adapter = HomePageViewPagerAdapter(viewList)
    }

    private lateinit var homePageView: View

    private fun initHomePage(homePageView: View) {
        fun initGoodsList() {
            thread {
                val msg: Message = Message()
                msg.what = GOODSLISTINIT
                msg.obj = AppDatabase.getDatabase().goodsWithSellerInfoDao().findAll()
                weakHandler.sendMessage(msg)
            }
        }
        this.homePageView = homePageView
        initGoodsList()
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
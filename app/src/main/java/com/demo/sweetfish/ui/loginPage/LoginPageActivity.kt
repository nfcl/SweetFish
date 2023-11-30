package com.demo.sweetfish.ui.loginPage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity
import com.demo.sweetfish.AppDatabase
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.logic.model.Goods
import com.demo.sweetfish.logic.model.GoodsPreviewImage
import com.demo.sweetfish.logic.model.User
import com.demo.sweetfish.ui.homePage.HomePageActivity
import com.demo.sweetfish.ui.registerPage.RegisterPageActivity
import com.example.sweetfish.R
import utils.DrawableUtils
import java.time.Instant
import kotlin.concurrent.thread
import kotlin.math.abs
import kotlin.random.Random

class LoginPageActivity : AppCompatActivity() {

    private val accountEdit: EditText by lazy { findViewById(R.id.LoginPageUserAccountEdit) }
    private val passwordEdit: EditText by lazy { findViewById(R.id.LoginPageUserPasswordEdit) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        initComponent()
//        listTest()
    }

    private fun initComponent() {
        findViewById<Button>(R.id.login_button).setOnClickListener { onLoginButtonClick() }
        findViewById<Button>(R.id.register_button).setOnClickListener { onRegisterButtonClick() }
    }

    private fun onLoginButtonClick() {
        thread {
            if (tryLogin()) {
                runOnUiThread {
                    startActivity(Intent(this, HomePageActivity::class.java))
                }
            }
        }
    }

    private fun onRegisterButtonClick() {
        startActivity(Intent(this, RegisterPageActivity::class.java))
    }

    @WorkerThread
    private fun tryLogin(): Boolean {
        val account: String = accountEdit.text.toString()
        if (account.isEmpty()) {
            runOnUiThread {
                Toast.makeText(this, "账号为空", Toast.LENGTH_SHORT).show()
            }
            return false
        }
        val password: String = passwordEdit.text.toString()
        if (password.isEmpty()) {
            runOnUiThread {
                Toast.makeText(this, "密码为空", Toast.LENGTH_SHORT).show()
            }
            return false
        }
        val userDao = AppDatabase.getDatabase().userDao()
        val userId: Long? = userDao.findByAccountAndPassword(account, password)
        if (userId == null) {
            runOnUiThread {
                Toast.makeText(this, "账号或密码不正确", Toast.LENGTH_SHORT).show()
            }
            return false
        }
        SweetFishApplication.setLoginUserId(userId)
        return true
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
                        i.toString(), i.toString(), DrawableUtils.getGradientDrawable(
                            100, 100, intArrayOf(
                                random.nextInt() % 0xFFFFFF, random.nextInt() % 0xFFFFFF
                            )
                        ), DrawableUtils.getGradientDrawable(
                            100, 100, intArrayOf(
                                random.nextInt() % 0xFFFFFF, random.nextInt() % 0xFFFFFF
                            )
                        )
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
                        "",
                        "",
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
                val randomNum = random.nextInt(9) + 1
                for (i in 0 until randomNum) {
                    result.add(
                        GoodsPreviewImage(
                            good.id, DrawableUtils.getGradientDrawable(
                                abs(random.nextInt()) % 100 + 500,
                                abs(random.nextInt()) % 100 + 500,
                                intArrayOf(
                                    random.nextInt() % 0xFFFFFF, random.nextInt() % 0xFFFFFF
                                )
                            )
                        )
                    )
                }
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
        }
    }
}
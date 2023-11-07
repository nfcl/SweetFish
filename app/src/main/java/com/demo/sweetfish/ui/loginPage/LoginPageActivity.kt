package com.demo.sweetfish.ui.loginPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.WorkerThread
import com.demo.sweetfish.AppDatabase
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.ui.homePage.HomePageActivity
import com.demo.sweetfish.ui.registerPage.RegisterPageActivity
import com.example.sweetfish.R
import kotlin.concurrent.thread

class LoginPageActivity : AppCompatActivity() {

    private val accountEdit: EditText by lazy { findViewById(R.id.LoginPageUserAccountEdit) }
    private val passwordEdit: EditText by lazy { findViewById(R.id.LoginPageUserPasswordEdit) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        initComponent()
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
}
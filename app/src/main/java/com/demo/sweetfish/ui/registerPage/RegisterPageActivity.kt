package com.demo.sweetfish.ui.registerPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.demo.sweetfish.AppDatabase
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.logic.dao.RegisterDao
import com.demo.sweetfish.logic.model.User
import com.example.sweetfish.R
import utils.DrawableUtils.Companion.toBytes
import kotlin.concurrent.thread

class RegisterPageActivity : AppCompatActivity() {

    private val accountEdit: EditText by lazy { findViewById(R.id.RegisterPageUserAccountEdit) }
    private val passwordEdit: EditText by lazy { findViewById(R.id.RegisterPageUserPasswordEdit) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)
        initComponent()
    }

    private fun initComponent() {
        findViewById<Button>(R.id.RegisterPageRegisterButton).setOnClickListener(::onRegisterButtonClick)
    }

    private fun onRegisterButtonClick(view: View) {
        val account: String = accountEdit.text.toString()
        val password: String = passwordEdit.text.toString()
        thread {
            val registerDao: RegisterDao =
                AppDatabase.getDatabase(SweetFishApplication.context).registerDao()
            if (registerDao.checkMultipleAccount(account) != 0) {
                runOnUiThread {
                    Toast.makeText(this, "账号已被注册", Toast.LENGTH_SHORT).show()
                }
            } else {
                AppDatabase.getDatabase(SweetFishApplication.context).userDao().insert(
                    User(
                        "昵称未设置",
                        null,
                        account,
                        password,
                        ContextCompat.getDrawable(this, R.mipmap.ic_launcher)!!.toBytes()
                    )
                )
                runOnUiThread {
                    Toast.makeText(this, "账号注册成功", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}
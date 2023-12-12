package com.demo.sweetfish.ui.registerPage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.sweetfish.R

class RegisterPageActivity : AppCompatActivity() {

    private val viewModel: RegisterPageActivityViewModel by lazy {
        ViewModelProvider(
            this, RegisterPageActivityViewModel.RegisterPageActivityViewModelFactory()
        )[RegisterPageActivityViewModel::class.java]
    }

    companion object {
        fun startActivity(appCompatActivity: AppCompatActivity) {
            appCompatActivity.startActivity(
                Intent(
                    appCompatActivity, RegisterPageActivity::class.java
                )
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)
        initComponent()
    }

    private fun initComponent() {
        findViewById<Button>(R.id.RegisterPageRegisterButton).setOnClickListener { onRegisterButtonClick() }
        findViewById<EditText>(R.id.RegisterPageUserAccountEdit).doAfterTextChanged { account ->
            viewModel.editAccount(
                account.toString()
            )
        }
        findViewById<EditText>(R.id.RegisterPageUserPasswordEdit).doAfterTextChanged { password ->
            viewModel.editPassword(
                password.toString()
            )
        }
    }

    private fun onRegisterButtonClick() {
        viewModel.register().onSuccess {
            it.observe(this) { result ->
                when (result) {
                    -1L -> Log.d("Mine", "未知原因注册失败")
                    -2L -> Log.d("Mine", "账号已存在")
                    else -> {
                        Log.d("Mine", "注册成功")
                        finish()
                    }
                }
            }
        }.onFailure {
            if (it.message != null) {
                Log.d("Mine", it.message!!)
            }
        }
    }
}
package com.demo.sweetfish.ui.loginPage

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.ui.homePage.HomePageActivity
import com.demo.sweetfish.ui.registerPage.RegisterPageActivity
import com.example.sweetfish.R

class LoginPageActivity : AppCompatActivity() {

    private val viewModel: LoginPageActivityViewModel by lazy {
        ViewModelProvider(
            this, LoginPageActivityViewModel.LoginPageActivityViewModelFactory()
        )[LoginPageActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        initComponent()
    }

    private fun initComponent() {
        findViewById<Button>(R.id.login_button).setOnClickListener { onLoginButtonClick() }
        findViewById<Button>(R.id.register_button).setOnClickListener { onRegisterButtonClick() }
        findViewById<EditText>(R.id.LoginPageUserAccountEdit).doAfterTextChanged { account ->
            viewModel.editAccount(
                account.toString()
            )
        }
        findViewById<EditText>(R.id.LoginPageUserPasswordEdit).doAfterTextChanged { password ->
            viewModel.editPassword(
                password.toString()
            )
        }
    }

    private fun onLoginButtonClick() {
        viewModel.login().onFailure {
            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
        }.onSuccess {
            it.observe(this) { loginUser ->
                if (loginUser != null) {
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show()
                    SweetFishApplication.setLoginUserId(loginUser.id)
                    HomePageActivity.startActivity(this)
                } else {
                    Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun onRegisterButtonClick() {
        RegisterPageActivity.startActivity(this)
    }
}
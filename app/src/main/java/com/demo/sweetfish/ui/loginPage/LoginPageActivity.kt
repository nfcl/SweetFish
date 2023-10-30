package com.demo.sweetfish.ui.loginPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.demo.sweetfish.ui.homePage.HomePageActivity
import com.demo.sweetfish.ui.registerPage.RegisterPageActivity
import com.example.sweetfish.R

class LoginPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        findViewById<Button>(R.id.login_button).setOnClickListener {
            startActivity(Intent(this, HomePageActivity::class.java))
        }
        findViewById<Button>(R.id.register_button).setOnClickListener {
            startActivity(Intent(this, RegisterPageActivity::class.java))
        }
    }
}
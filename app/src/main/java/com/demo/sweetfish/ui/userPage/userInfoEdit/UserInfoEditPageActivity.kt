package com.demo.sweetfish.ui.userPage.userInfoEdit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.ui.component.RoundImageView
import com.example.sweetfish.R
import utils.DrawableUtils
import kotlin.concurrent.thread

class UserInfoEditPageActivity : AppCompatActivity() {

    private val viewModel: UserInfoEditPageActivityViewModel by lazy {
        ViewModelProvider(
            this, UserInfoEditPageActivityViewModel.UserInfoEditPageActivityViewModelFactory()
        )[UserInfoEditPageActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info_edit_page)
        initComponent()
    }

    private fun initComponent() {
        findViewById<ImageView>(R.id.UserInfoEditPageReturnButton).setOnClickListener {
            finish()
        }
        val avatarImageView: RoundImageView = findViewById(R.id.UserInfoEditPageAvatarImageView)
        val idTextView: TextView = findViewById(R.id.UserInfoEditPageIdTextView)
        val sexTextView: TextView = findViewById(R.id.UserInfoEditPageSexTextView)
        val nameTextView: TextView = findViewById(R.id.UserInfoEditPageNameTextView)
        viewModel.userId.observe(this) { id -> idTextView.text = id.toString() }
        viewModel.userName.observe(this) { name -> sexTextView.text = name }
        viewModel.userSex.observe(this) { sex -> nameTextView.text = sex }
        viewModel.userAvatar.observe(this) { avatar -> avatarImageView.setImageDrawable(avatar) }
        val avatarSelectLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val currentUri: Uri = result.data?.data!!
                thread {
                    val inputStream = contentResolver.openInputStream(currentUri)!!
                    viewModel.setAvatar(
                        DrawableUtils.createDrawableFromByteArray(
                            inputStream.readBytes(), ""
                        )
                    )
                    inputStream.close()
                }
            }
        }
        findViewById<RelativeLayout>(R.id.UserInfoEditPageAvatarEditButton).setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            avatarSelectLauncher.launch(intent)
        }
    }
}
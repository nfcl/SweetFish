package com.demo.sweetfish.ui.goodsPage

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.logic.repository.ImageSourceRepository
import com.demo.sweetfish.ui.component.RoundImageView
import com.demo.sweetfish.ui.messagePage.MessagePageActivity
import com.demo.sweetfish.ui.userPage.others.OthersUserPageActivity
import com.demo.sweetfish.ui.userPage.personal.PersonalUserPageActivity
import com.example.sweetfish.R
import kotlinx.coroutines.Dispatchers

class GoodsPageActivity : AppCompatActivity() {

    private val viewModel: GoodsPageActivityViewModel by lazy {
        ViewModelProvider(
            this, GoodsPageActivityViewModel.GoodsPageActivityViewModelFactory()
        )[GoodsPageActivityViewModel::class.java]
    }

    companion object {
        fun startActivity(appCompatActivity: AppCompatActivity, goodsId: Long) {
            val newIntent = Intent(appCompatActivity, GoodsPageActivity::class.java)
            newIntent.putExtra("GoodsId", goodsId)
            appCompatActivity.startActivity(newIntent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_page)
        val goodsId = intent.getLongExtra("GoodsId", -1)
        if (goodsId == (-1).toLong()) {
            finish()
        }
        viewModel.setGoodsId(goodsId)
        initComponent()
    }

    fun initComponent() {
        val goodsPreviewPictureView =
            findViewById<GoodsPageActivityPreviewPictureView>(R.id.GoodsPagePreviewImageView)
        viewModel.goodsPreviewImage.observe(this) { data ->
            liveData(Dispatchers.IO) {
                emit(List(data.size) { ImageSourceRepository.findByIdWithSuspend(data[it])?.content })
            }.observe(this) { list -> goodsPreviewPictureView.setImageList(list) }
        }
        val goodsPriceTextView = findViewById<TextView>(R.id.GoodsPageGoodsPriceTextView)
        viewModel.goodsPrice.observe(this) { price -> goodsPriceTextView.text = "￥$price" }
        val goodsDescribeTextView = findViewById<TextView>(R.id.GoodsPageGoodsDescribeTextView)
        viewModel.goodsDescribe.observe(this) { describe -> goodsDescribeTextView.text = describe }
        val sellerInfoButton = findViewById<LinearLayout>(R.id.GoodsPageSellerInfoButton)
        sellerInfoButton.setOnClickListener {
            if (viewModel.sellerId.value!! == SweetFishApplication.loginUserId.value!!) {
                PersonalUserPageActivity.startActivity(this)
            } else {
                OthersUserPageActivity.startActivity(this, viewModel.sellerId.value!!)
            }
        }
        val sellerNameTextView = findViewById<TextView>(R.id.GoodsPageSellerNameTextView)
        viewModel.sellerName.observe(this) { name -> sellerNameTextView.text = name }
        val sellerAvatarTextView =
            findViewById<RoundImageView>(R.id.GoodsPageSellerAvatarRoundImageView)
        viewModel.sellerAvatar.observe(this) { avatar ->
            sellerAvatarTextView.setImageDrawable(
                avatar.content
            )
        }
        val sellerFollowButton = findViewById<TextView>(R.id.GoodsPageSellerFollowButton)
        viewModel.sellerId.observe(this) { id ->
            if (id == SweetFishApplication.loginUserId.value) {
                sellerFollowButton.visibility = View.GONE
                sellerFollowButton.isClickable = false
            } else {
                sellerFollowButton.visibility = View.VISIBLE
                sellerFollowButton.isClickable = true
            }
        }
        val followedText = getString(R.string.OthersUserPageFollowButton_Followed_Text)
        val unFollowedText = getString(R.string.OthersUserPageFollowButton_UnFollowed_Text)
        val followedBackgroundTint =
            ColorStateList.valueOf(getColor(R.color.OthersUserPageFollowButton_Followed_BackgroundTint))
        val unFollowedBackgroundTint =
            ColorStateList.valueOf(getColor(R.color.OthersUserPageFollowButton_UnFollowed_BackgroundTint))
        val followedTextColor =
            ColorStateList.valueOf(getColor(R.color.OthersUserPageFollowButton_Followed_TextColor))
        val unFollowedTextColor =
            ColorStateList.valueOf(getColor(R.color.OthersUserPageFollowButton_UnFollowed_TextColor))
        viewModel.isFollowed.observe(this) { isFollowed ->
            if (isFollowed) {
                sellerFollowButton.text = followedText
                sellerFollowButton.backgroundTintList = followedBackgroundTint
                sellerFollowButton.setTextColor(followedTextColor)
                sellerFollowButton.setOnClickListener {
                    viewModel.setFollowState(false)
                }
            } else {
                sellerFollowButton.text = unFollowedText
                sellerFollowButton.backgroundTintList = unFollowedBackgroundTint
                sellerFollowButton.setTextColor(unFollowedTextColor)
                sellerFollowButton.setOnClickListener {
                    viewModel.setFollowState(true)
                }
            }
        }

        val wantButton = findViewById<TextView>(R.id.GoodsPageWantBotton)
        wantButton.setOnClickListener {
            viewModel.wantGoods { chatId ->
                MessagePageActivity.startActivity(
                    this, chatId
                )
            }
        }
    }
}
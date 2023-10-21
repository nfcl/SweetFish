package com.demo.sweetfish.ui.homePage

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.sweetfish.R

/**
 * 主页底部导航栏
 *
 * 用于在商品推荐页面和用户页面间切换,以及商品发布页面的入口
 * @property homePageButton         主页面标签按钮
 * @property userPageButton         用户页面标签按钮
 * @property goodsPublishPageButton 商品发布页面跳转按钮
 */
class HomePageNavigationBar(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private val homePageButton: Button
    private val userPageButton: ImageView
    private val goodsPublishPageButton: Button

    init {
        LayoutInflater.from(context).inflate(R.layout.activity_home_page_navigationbar, this)
        homePageButton = findViewById(R.id.HomePageButton)
        goodsPublishPageButton = findViewById(R.id.GoodsPublishPageButton)
        userPageButton = findViewById(R.id.UserPageButton)
    }

    fun setHomePageButtonOnClickListener(event: () -> Unit) {
        homePageButton.setOnClickListener { event() }
    }

    fun setGoodsPublishPageButtonOnClickListener(event: () -> Unit) {
        goodsPublishPageButton.setOnClickListener { event() }
    }

    fun setUserPageButtonOnClickListener(event: () -> Unit) {
        userPageButton.setOnClickListener { event() }
    }
}
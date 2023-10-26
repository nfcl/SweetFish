package com.demo.sweetfish.ui.homePage

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.demo.sweetfish.logic.model.GoodsWithSellerInfo
import com.example.sweetfish.R

class HomePageGoodsList(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs) {

    private lateinit var goodsListView: RecyclerView

    init {
        LayoutInflater.from(context).inflate(R.layout.activity_home_page_goodslist, this)
        goodsListInit()
    }

    private fun goodsListInit() {
        goodsListView = findViewById(R.id.GoodsListView_RecyclerView)
        goodsListView.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        goodsListView.adapter = HomePageGoodeListAdapter(ArrayList<GoodsWithSellerInfo>())
    }

    fun setGoodsList(goodsList: List<GoodsWithSellerInfo>) {
        goodsListView.adapter = HomePageGoodeListAdapter(goodsList)
    }
}
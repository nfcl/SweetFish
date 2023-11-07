package com.demo.sweetfish.ui.homePage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.demo.sweetfish.logic.model.GoodsWithSellerInfo
import com.example.sweetfish.R

class HomePageGoodsListAdapter(
    private val goodsList: List<GoodsWithSellerInfo>,
    private var onGoodsClickEvent: (goods: GoodsWithSellerInfo) -> Unit = {},
    private var onSellerClickEvent: (sellerId: Long) -> Unit = {},
) :
    Adapter<HomePageGoodsListAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val previewImage: ImageView = view.findViewById(R.id.GoodsPreviewImage)
        val nameText: TextView = view.findViewById(R.id.GoodsNameText)
        val priceText: TextView = view.findViewById(R.id.GoodsPriceText)
        val sellerAvatarImage: ImageView = view.findViewById(R.id.SellerAvatarImage)
        val sellerNameText: TextView = view.findViewById(R.id.SellerNameText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_home_page_goodlist_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val goods = goodsList[position]
        holder.previewImage.setImageDrawable(goods.goodsPreviewPic)
        holder.nameText.text = goods.goodsTitle
        holder.priceText.text = "￥${goods.goodsPrice}"
        holder.sellerAvatarImage.setImageDrawable(goods.sellerAvatarPic)
        holder.sellerNameText.text = goods.sellerName
        holder.itemView.findViewById<LinearLayout>(R.id.GoodsInfoButton)
            .setOnClickListener { onGoodsClickEvent(goods) }
        holder.itemView.findViewById<LinearLayout>(R.id.SellerInfoButton)
            .setOnClickListener { onSellerClickEvent(goods.sellerId) }
    }

    override fun getItemCount(): Int {
        return goodsList.size
    }

    fun setOnGoodsClickEvent(event: (goods: GoodsWithSellerInfo) -> Unit) {
        onGoodsClickEvent = event
    }

    fun setOnSellerClickEvent(event: (sellerId: Long) -> Unit) {
        onSellerClickEvent = event
    }

}
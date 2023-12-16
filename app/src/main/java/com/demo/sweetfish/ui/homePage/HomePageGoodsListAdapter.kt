package com.demo.sweetfish.ui.homePage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.demo.sweetfish.logic.model.GoodsWithSellerInfo
import com.demo.sweetfish.logic.repository.ImageSourceRepository
import com.example.sweetfish.R

class HomePageGoodsListAdapter(
    private val appCompatActivity: AppCompatActivity,
    private var mGoodsList: List<GoodsWithSellerInfo>,
    private var onGoodsClickEvent: (goodsId: Long) -> Unit,
    private var onSellerClickEvent: (sellerId: Long) -> Unit,
) : Adapter<HomePageGoodsListAdapter.ViewHolder>() {

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
        val goods = mGoodsList[position]
        ImageSourceRepository.findById(goods.goodsPreview).observe(appCompatActivity) {
            holder.previewImage.setImageDrawable(it?.content)
        }
        holder.nameText.text = goods.goodsTitle
        holder.priceText.text = "￥${goods.goodsPrice}"
        ImageSourceRepository.findById(goods.sellerAvatar).observe(appCompatActivity) {
            holder.sellerAvatarImage.setImageDrawable(it?.content)
        }
        holder.sellerNameText.text = goods.sellerName ?: goods.sellerId.toString()
        holder.itemView.findViewById<LinearLayout>(R.id.GoodsInfoButton).setOnClickListener {
            onGoodsClickEvent(goods.goodsId)
        }
        holder.itemView.findViewById<LinearLayout>(R.id.SellerInfoButton).setOnClickListener {
            onSellerClickEvent(goods.sellerId)
        }
    }

    override fun getItemCount(): Int {
        return mGoodsList.size
    }

    fun setListData(goodsList: List<GoodsWithSellerInfo>) {
        notifyItemRangeRemoved(0, mGoodsList.size)
        mGoodsList = goodsList
        notifyItemRangeInserted(0, mGoodsList.size)
    }

}
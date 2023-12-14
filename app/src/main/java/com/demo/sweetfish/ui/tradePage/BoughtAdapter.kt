package com.demo.sweetfish.ui.tradePage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.demo.sweetfish.logic.model.GoodsBuyInfo
import com.demo.sweetfish.logic.repository.ImageSourceRepository
import com.example.sweetfish.R

class BoughtAdapter(
    private val appCompatActivity: AppCompatActivity,
    private var goodsList: List<GoodsBuyInfo>,
) : RecyclerView.Adapter<BoughtAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val sellerAvatar: ImageView = view.findViewById(R.id.SellerImage)
        val sellerName: TextView = view.findViewById(R.id.SellerNameText)
        val goodsName: TextView = view.findViewById(R.id.UserPageBoughtItemScrible)
        val goodsPreview: ImageView = view.findViewById(R.id.BoughtItemImage)
        val goodsPrice: TextView = view.findViewById(R.id.Bought_goodsprice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_user_page_mybought_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = goodsList[position]

        ImageSourceRepository.findById(item.sellerAvatar).observe(appCompatActivity) {
            holder.sellerAvatar.setImageDrawable(it?.content)
        }
        holder.sellerName.text = item.sellerName
        holder.goodsName.text = item.goodsName
        ImageSourceRepository.findById(item.goodsPreview).observe(appCompatActivity) {
            holder.goodsPreview.setImageDrawable(it?.content)
        }
        holder.goodsPrice.text = "￥${item.goodsPrice}"

    }

    override fun getItemCount(): Int {
        return goodsList.size
    }

    fun setList(newList: List<GoodsBuyInfo>) {
        notifyItemRangeRemoved(0, goodsList.size)
        goodsList = newList
        notifyItemRangeInserted(0, goodsList.size)
    }

}
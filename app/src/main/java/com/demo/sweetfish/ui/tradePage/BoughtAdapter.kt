package com.demo.sweetfish.ui.tradePage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.demo.sweetfish.logic.model.GoodsBuyInfo
import com.example.sweetfish.R

class BoughtAdapter(val GoodsList:List<GoodsBuyInfo>): RecyclerView.Adapter<BoughtAdapter.ViewHolder>(){
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val sellerAvatar: ImageView = view.findViewById(R.id.SellerImage)
        val sellername: TextView = view.findViewById(R.id.SellerNameText)
        val goodsName: TextView = view.findViewById(R.id.UserPageBoughtItemScrible)
        val goodsPreview: ImageView = view.findViewById(R.id.BoughtItemImage)
        val goodsPrice:TextView = view.findViewById(R.id.Bought_goodsprice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.activity_user_page_mybought_list_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = GoodsList[position]


        holder.sellerAvatar.setImageDrawable(item.goodsPreview)
        holder.sellername.text = item.sellerName
        holder.goodsName.text = item.goodsName
        holder.goodsPreview.setImageDrawable(item.goodsPreview)
        holder.goodsPrice.text = "$" + item.goodsPrice.toString()

    }

    override fun getItemCount(): Int {
        return GoodsList.size
    }
}
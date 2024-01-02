package com.demo.sweetfish.ui.tradePage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.demo.sweetfish.logic.model.GoodsWithSellerInfo
import com.demo.sweetfish.logic.repository.ImageSourceRepository
import com.example.sweetfish.R

class PublishAdapter(
    private val appCompatActivity: AppCompatActivity,
    private var publishlish: List<GoodsWithSellerInfo>,
) :
    RecyclerView.Adapter<PublishAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val goodsprice: TextView = view.findViewById(R.id.PublishPrice)
        val goodsName: TextView = view.findViewById(R.id.Publish_Describe)
        val goodsPreview: ImageView = view.findViewById(R.id.Publish_Image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_user_page_sold_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = publishlish[position]
        holder.goodsprice.text = "￥$item.goodsPrice"
        holder.goodsName.text = item.goodsTitle
        ImageSourceRepository.findById(item.goodsPreview).observe(appCompatActivity) {
            holder.goodsPreview.setImageDrawable(it?.content)
        }

    }

    override fun getItemCount(): Int {
        return publishlish.size
    }

    fun setListData(goodsList: List<GoodsWithSellerInfo>) {
        notifyItemRangeRemoved(0, publishlish.size)
        publishlish = goodsList
        notifyItemRangeInserted(0, publishlish.size)
    }
}
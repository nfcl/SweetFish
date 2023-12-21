package com.demo.sweetfish.ui.goodsPublishPage

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.sweetfish.R

class GoodsImageAdapter(
    private val images: MutableList<Drawable>,
    private var onImageDeleteClickListener: ((Int) -> Unit)? = {},
) : RecyclerView.Adapter<GoodsImageAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView =
            view.findViewById(R.id.GoodsPublishPageActivityPreviewImageListItemImageView)
        val deleteIcon: ImageView =
            view.findViewById(R.id.GoodsPublishPageActivityPreviewImageListItemDeleteIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_goods_publish_page_preview_image_list_item, parent, false)
        return ViewHolder(view)
    }

    fun setOnImageDeleteClickListener(listener: (Int) -> Unit) {
        onImageDeleteClickListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageDrawable(images[position])
        holder.deleteIcon.setOnClickListener {
            onImageDeleteClickListener?.invoke(position)
        }
    }

    override fun getItemCount() = images.size

    fun updateImages(newImages: List<Drawable>) {
        notifyItemRangeRemoved(0, images.size)
        images.clear()
        images.addAll(newImages)
        notifyItemRangeInserted(0, images.size)
    }

}

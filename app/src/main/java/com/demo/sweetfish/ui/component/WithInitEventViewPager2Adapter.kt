package com.demo.sweetfish.ui.component

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.demo.sweetfish.SweetFishApplication
import com.example.sweetfish.R

class WithInitEventViewPager2Adapter(
    private val views: List<Pair<View, (View) -> Unit>>,
) : RecyclerView.Adapter<WithInitEventViewPager2Adapter.ViewHolder>() {

    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(SweetFishApplication.context).inflate(
            R.layout.viewpager2_framework, null
        )
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return views.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        views[position].second(views[position].first)
        (holder.view as LinearLayout).addView(views[position].first)
    }

}
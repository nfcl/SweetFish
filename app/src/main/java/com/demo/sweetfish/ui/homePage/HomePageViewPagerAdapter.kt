package com.demo.sweetfish.ui.homePage

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class HomePageViewPagerAdapter(private var viewLists: List<Pair<View, (View) -> Unit>>? = null) :
    PagerAdapter() {
    override fun getCount(): Int {
        return viewLists!!.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(viewLists!![position].first)
        viewLists!![position].second(viewLists!![position].first)
        return viewLists!![position].first
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(viewLists!![position].first)
    }
}
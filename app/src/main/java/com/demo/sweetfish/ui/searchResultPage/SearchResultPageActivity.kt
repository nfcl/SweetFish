package com.demo.sweetfish.ui.searchResultPage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.ui.component.WithInitEventViewPager2Adapter
import com.demo.sweetfish.ui.homePage.HomePageGoodsListAdapter
import com.demo.sweetfish.ui.userPage.others.OthersUserPageActivity
import com.demo.sweetfish.ui.userPage.personal.PersonalUserPageActivity
import com.example.sweetfish.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class SearchResultPageActivity : AppCompatActivity() {

    private val viewModel: SearchResultPageActivityViewModel by lazy {
        ViewModelProvider(
            this, SearchResultPageActivityViewModel.SearchResultPageActivityViewModelFactory()
        )[SearchResultPageActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result_page)
        initViewPager()
        initViewModel()
    }

    private fun initViewPager() {
        val viewPager = findViewById<ViewPager2>(R.id.SearchResultPageViewPager2)
        val tabLayout = findViewById<TabLayout>(R.id.SearchResultPageViewPager2TabLayout)
        val goodsListView = LayoutInflater.from(SweetFishApplication.context).inflate(
            R.layout.activity_search_result_page_goodslist_view, null
        )
        goodsListView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
        val userListView = LayoutInflater.from(SweetFishApplication.context).inflate(
            R.layout.activity_search_result_page_userlist_view, null
        )
        userListView.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        )
        val views: List<Pair<View, (View) -> Unit>> = listOf(
            Pair(
                goodsListView, ::initGoodsListView
            ), Pair(
                userListView, ::initUserListView
            )
        )
        viewPager.adapter = WithInitEventViewPager2Adapter(views)
        val tabTextList: List<String> = listOf("商品", "用户")
        TabLayoutMediator(
            tabLayout, viewPager
        ) { tab, position ->
            tab.text = tabTextList[position]
        }.attach()
    }

    private fun initGoodsListView(goodsListView: View) {
        val goodsList: RecyclerView = goodsListView.findViewById(R.id.GoodsListView_RecyclerView)
        goodsList.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        goodsList.adapter = HomePageGoodsListAdapter(ArrayList(), {}, { userId ->
            if (userId == SweetFishApplication.loginUserId.value!!) {
                PersonalUserPageActivity.startActivity(this)
            } else {
                OthersUserPageActivity.startActivity(this, userId)
            }
        })
        viewModel.goodsList.observe(this) { listData ->
            (goodsList.adapter as HomePageGoodsListAdapter).setListData(listData)
        }
    }

    private fun initUserListView(userListView: View) {
        val userList: RecyclerView = userListView.findViewById(R.id.SearchResultPageUserList)
        userList.layoutManager = LinearLayoutManager(this)
        userList.adapter = SearchResultPageUserListAdapter(this, listOf()) { userId ->
            if (userId == SweetFishApplication.loginUserId.value!!) {
                PersonalUserPageActivity.startActivity(this)
            } else {
                OthersUserPageActivity.startActivity(this, userId)
            }
        }
        viewModel.userList.observe(this) { listData ->
            (userList.adapter as SearchResultPageUserListAdapter).setListData(listData)
        }
    }

    private fun initViewModel() {
        val searchContent = intent.getStringExtra("SearchContent") ?: ""
        val searchBarTextView: TextView = findViewById(R.id.SearchContent_EditText)
        searchBarTextView.text = searchContent
        viewModel.setSearchContent(searchContent)
    }

}
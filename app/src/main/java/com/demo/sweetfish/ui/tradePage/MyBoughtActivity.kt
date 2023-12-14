package com.demo.sweetfish.ui.tradePage

import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sweetfish.R

class MyBoughtActivity : AppCompatActivity() {

    private val viewModel: MyBoughtPageActivityViewModel by lazy {
        ViewModelProvider(
            this, MyBoughtPageActivityViewModel.MyBoughtPageActivityViewModelFactory()
        )[MyBoughtPageActivityViewModel::class.java]
    }

    private lateinit var tagItems: Map<MyBoughtPageActivityViewModel.BuyState, TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page_userpageview_mybought)
        initComponent()
    }

    private fun initComponent() {
        findViewById<ImageView>(R.id.MyBoughtReturnButton).setOnClickListener { finish() }
        val goodsList = findViewById<RecyclerView>(R.id.UserBoughtRecyclerView)
        val layoutManager = LinearLayoutManager(this)
        goodsList.layoutManager = layoutManager
        goodsList.adapter = BoughtAdapter(this, listOf())

        viewModel.buyList.observe(this) { list ->
            (goodsList.adapter as BoughtAdapter).setList(list)
        }

        val boughtAllTag = findViewById<TextView>(R.id.Bought_All)
        val boughtPayTag = findViewById<TextView>(R.id.Bought_Pay)
        val boughtSendTag = findViewById<TextView>(R.id.Bought_Send)
        val boughtShouTag = findViewById<TextView>(R.id.Bought_Shou)
        val boughtPingTag = findViewById<TextView>(R.id.Bought_Ping)
        val boughtTuiTag = findViewById<TextView>(R.id.Bought_Tui)

        tagItems = mapOf<MyBoughtPageActivityViewModel.BuyState, TextView>(
            MyBoughtPageActivityViewModel.BuyState.All to boughtAllTag,
            MyBoughtPageActivityViewModel.BuyState.Pay to boughtPayTag,
            MyBoughtPageActivityViewModel.BuyState.Send to boughtSendTag,
            MyBoughtPageActivityViewModel.BuyState.Shou to boughtShouTag,
            MyBoughtPageActivityViewModel.BuyState.Ping to boughtPingTag,
            MyBoughtPageActivityViewModel.BuyState.Tui to boughtTuiTag
        )

        for (item in tagItems) {
            item.value.setOnClickListener { select(item.key) }
        }
    }

    private fun select(tag: MyBoughtPageActivityViewModel.BuyState) {
        for (item in tagItems) {
            if (item.key == tag) {
                setTagSelect(item.value)
            } else {
                setTagUnSelect(item.value)
            }
        }
        viewModel.setSearchBuyState(tag)
    }

    private fun setTagUnSelect(tagTextView: TextView) {
        tagTextView.setTextColor(getColor(R.color.UserPageTagLayoutItemUnSelectText))
        tagTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
    }

    private fun setTagSelect(tagTextView: TextView) {
        tagTextView.setTextColor(getColor(R.color.black))
        tagTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        tagTextView.setTypeface(null, Typeface.BOLD)
    }
}
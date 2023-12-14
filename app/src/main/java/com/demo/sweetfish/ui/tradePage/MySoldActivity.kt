package com.demo.sweetfish.ui.tradePage

import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.demo.sweetfish.logic.model.GoodsBuyInfo
import com.example.sweetfish.R


class MySoldActivity : AppCompatActivity() {
    private val boughtlist = ArrayList<GoodsBuyInfo>()

    private lateinit var tagitems: Map<SoldTag, TextView>

    enum class SoldTag {
        All,
        Pay,
        Send,
        Tui,
        Shou,
        Ping
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page_userpageview_mysold)
        initComponent()
    }

    private fun initComponent() {
        findViewById<ImageView>(R.id.MySoldReturnButton).setOnClickListener { finish() }
        val recyclerView = findViewById<RecyclerView>(R.id.SoldRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = BoughtAdapter(this, boughtlist)

        val Sold_All = findViewById<TextView>(R.id.Sold_All)
        val Sold_Pay = findViewById<TextView>(R.id.Sold_Pay)
        val Sold_Send = findViewById<TextView>(R.id.Sold_Send)
        val Sold_Shou = findViewById<TextView>(R.id.Sold_Shou)
        val Sold_Ping = findViewById<TextView>(R.id.Sold_Ping)
        val Sold_Tui = findViewById<TextView>(R.id.Sold_Tui)
        tagitems = mapOf<SoldTag, TextView>(
            SoldTag.All to Sold_All,
            SoldTag.Pay to Sold_Pay,
            SoldTag.Send to Sold_Send,
            SoldTag.Shou to Sold_Shou,
            SoldTag.Tui to Sold_Tui,
            SoldTag.Ping to Sold_Ping
        )
        for (item in tagitems) {
            item.value.setOnClickListener { select(item.key) }
        }
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

    private fun select(tag: SoldTag) {
        for (items in tagitems) {
            if (items.key == tag) {
                setTagSelect(items.value)
            } else {
                setTagUnSelect(items.value)
            }
        }
    }
}
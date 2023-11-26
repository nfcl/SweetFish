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
import utils.DrawableUtils
import java.time.Instant
import java.util.Date

class MyBoughtActivity : AppCompatActivity() {
    private val boughtlist = ArrayList<GoodsBuyInfo>()
    private lateinit var tagItems:Map<BoughtTag, TextView>
    private lateinit var recyclerView:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page_userpageview_mybought)
        initComponent()
        initlist()
    }
    enum class BoughtTag{
        All,
        Pay,
        Send,
        Shou,
        Ping,
        Tui
    }
    private fun initComponent(){
        findViewById<ImageView>(R.id.MyBoughtReturnButton).setOnClickListener{finish()}
        recyclerView = findViewById<RecyclerView>(R.id.UserBoughtRecyclerView)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = BoughtAdapter(boughtlist)
        val Bought_All = findViewById<TextView>(R.id.Bought_All)
        val Bought_Pay = findViewById<TextView>(R.id.Bought_Pay)
        val Bought_Send = findViewById<TextView>(R.id.Bought_Send)
        val Bought_Shou = findViewById<TextView>(R.id.Bought_Shou)
        val Bought_Ping = findViewById<TextView>(R.id.Bought_Ping)
        val Bought_Tui = findViewById<TextView>(R.id.Bought_Tui)

        tagItems = mapOf<BoughtTag, TextView>(
            BoughtTag.All to Bought_All,
            BoughtTag.Pay to Bought_Pay,
            BoughtTag.Send to Bought_Send,
            BoughtTag.Shou to Bought_Shou,
            BoughtTag.Ping to Bought_Ping,
            BoughtTag.Tui to Bought_Tui
        )

        for (item in tagItems){
            item.value.setOnClickListener{select(item.key)}
        }
    }

    private fun initlist(){
        for (i in 1..10) {
            boughtlist.add(
                GoodsBuyInfo(
                    i.toLong(),
                    Date(Instant.now().epochSecond),
                    i.toLong(),
                    "用户$i",
                    DrawableUtils.getGradientDrawable(),
                    i.toLong(),
                    "商品$i",
                    DrawableUtils.getGradientDrawable(),
                    i.toDouble()
                )
            )
        }
    }
    private fun select(tag: BoughtTag) {
        for (item in tagItems) {
            if (item.key == tag) {
                setTagSelect(item.value)
            } else {
                setTagUnSelect(item.value)
            }
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
}
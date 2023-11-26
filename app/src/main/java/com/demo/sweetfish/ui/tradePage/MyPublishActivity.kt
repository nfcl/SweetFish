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

class MyPublishActivity : AppCompatActivity() {

    private val goodsBuyList = ArrayList<GoodsBuyInfo>()

    private lateinit var tagItems: Map<PublishTag, TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page_userpageview_mypublish)
        initComponent()
        listInit()
    }

    enum class PublishTag {
        Sold,
        Paper,
        Down
    }

    private fun initComponent() {

        findViewById<ImageView>(R.id.MyPublishReturnButton).setOnClickListener { finish() }

        val recyclerview = findViewById<RecyclerView>(R.id.PublishRecyclerView)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = PublishAdapter(goodsBuyList)

        val publishTagItemSold = findViewById<TextView>(R.id.Publish_Sold)
        val publishTagItemPaper = findViewById<TextView>(R.id.Publish_Paper)
        val publishTagItemDown = findViewById<TextView>(R.id.Publish_Down)
        tagItems = mapOf<PublishTag, TextView>(
            PublishTag.Sold to publishTagItemSold,
            PublishTag.Paper to publishTagItemPaper,
            PublishTag.Down to publishTagItemDown
        )
        for (item in tagItems) {
            item.value.setOnClickListener { select(item.key) }
        }

    }

    private fun listInit() {
        for (i in 1..10) {
            goodsBuyList.add(
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

    private fun select(tag: PublishTag) {
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
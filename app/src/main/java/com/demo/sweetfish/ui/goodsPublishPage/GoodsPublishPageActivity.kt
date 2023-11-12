package com.demo.sweetfish.ui.goodsPublishPage

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.demo.sweetfish.AppDatabase
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.logic.model.Goods
import com.example.sweetfish.R
import kotlin.concurrent.thread

class GoodsPublishPageActivity : AppCompatActivity() {

    private val titleEdit: EditText by lazy { findViewById(R.id.GoodsTitle) }
    private val infoEdit: EditText by lazy { findViewById(R.id.GoodsInfo) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_publish_page)

        findViewById<TextView>(R.id.GoodsPublishPageCancelButton).setOnClickListener {
            finish()
        }
        initComponent()
    }

    private fun initComponent() {
        findViewById<TextView>(R.id.GoodsPublishPageButton).setOnClickListener { onGoodsPublishPageButton() }
    }

    private fun onGoodsPublishPageButton() {
        val title: String = titleEdit.text.toString()
        val info: String = infoEdit.text.toString()

        thread {
            if (title.isEmpty()) {
                runOnUiThread {
                    Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show()
                }
            } else if (info.isEmpty()) {
                runOnUiThread {
                    Toast.makeText(this, "请输入有关商品的描述", Toast.LENGTH_SHORT).show()
                }
            } else {
                AppDatabase.getDatabase(SweetFishApplication.context).goodsDao().insert(
                    Goods(
                        title, 10.0, info, 0
                    )
                )
                runOnUiThread {
                    Toast.makeText(this, "商品发布成功", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}
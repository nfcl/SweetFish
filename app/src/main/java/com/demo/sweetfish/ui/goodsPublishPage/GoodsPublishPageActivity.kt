package com.demo.sweetfish.ui.goodsPublishPage

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.demo.sweetfish.AppDatabase
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.logic.model.Goods
import com.example.sweetfish.R
import kotlin.concurrent.thread

class GoodsPublishPageActivity : AppCompatActivity() {

    private val titleEdit: EditText by lazy { findViewById(R.id.GoodsPublishPageGoodsTitleEditText) }
    private val infoEdit: EditText by lazy { findViewById(R.id.GoodsPublishPageGoodsInfoEditText) }
    private val priceEdit: TextView by lazy { findViewById(R.id.GoodsPublishPagePriceEditText) }
    private var goodsPriceNum: Double = 0.0

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val returnData = result.data?.getDoubleExtra("GoodsPrice", 0.00)!!
            priceEdit.text = "￥$returnData"
            goodsPriceNum = returnData
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_publish_page)
        findViewById<TextView>(R.id.GoodsPublishPageCancelButton).setOnClickListener {
            finish()
        }
        findViewById<TextView>(R.id.GoodsPublishPagePriceEditText).setOnClickListener {
            launcher.launch(Intent(this, GoodsPriceSettingPageActivity::class.java))
        }
        initComponent()
    }

    private fun initComponent() {
        findViewById<TextView>(R.id.GoodsPublishPagePublishButton).setOnClickListener {
            onPublishButtonClick()
        }
    }

    private fun onPublishButtonClick() {
        val title: String = titleEdit.text.toString()
        val info: String = infoEdit.text.toString()

        if (title.isEmpty()) {
            Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show()
        } else if (info.isEmpty()) {
            Toast.makeText(this, "请输入有关商品的描述", Toast.LENGTH_SHORT).show()
        } else {
            thread {
                AppDatabase.getDatabase(SweetFishApplication.context).goodsDao().insert(
                    Goods(
                        title, goodsPriceNum, info, locationString,SweetFishApplication.loginUserId.value!!
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
package com.demo.sweetfish.ui.goodsPublishPage

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.sweetfish.R

class GoodsPriceSettingPageActivity : AppCompatActivity() {

    private val priceText: EditText by lazy { findViewById(R.id.GoodsPriceSettingPagePriceEditText) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_publish_page_publisher_price_setting_page)
        init()
    }

    private fun init() {

        priceText.doAfterTextChanged {
            priceText.setSelection(it?.length!!)
        }

        val keyBoardNumber = arrayOf<TextView>(
            findViewById(R.id.GoodsPriceSettingPageKeyBoarNumber0Button),
            findViewById(R.id.GoodsPriceSettingPageKeyBoarNumber1Button),
            findViewById(R.id.GoodsPriceSettingPageKeyBoarNumber2Button),
            findViewById(R.id.GoodsPriceSettingPageKeyBoarNumber3Button),
            findViewById(R.id.GoodsPriceSettingPageKeyBoarNumber4Button),
            findViewById(R.id.GoodsPriceSettingPageKeyBoarNumber5Button),
            findViewById(R.id.GoodsPriceSettingPageKeyBoarNumber6Button),
            findViewById(R.id.GoodsPriceSettingPageKeyBoarNumber7Button),
            findViewById(R.id.GoodsPriceSettingPageKeyBoarNumber8Button),
            findViewById(R.id.GoodsPriceSettingPageKeyBoarNumber9Button)
        )

        for (number in 0..9) {
            keyBoardNumber[number].setOnClickListener { onClickNumber(number) }
        }

        val keyBoardEnter = findViewById<TextView>(R.id.GoodsPriceSettingPageKeyBoarEnterButton)
        val keyBoardPoint = findViewById<TextView>(R.id.GoodsPriceSettingPageKeyBoarPointButton)
        val keyBoardBackSpace = findViewById<LinearLayout>(R.id.GoodsPriceSettingPageKeyBoarBackSpaceButton)

        keyBoardPoint.setOnClickListener { onClickPoint() }
        keyBoardBackSpace.setOnClickListener { onCLickBackSpace() }
        keyBoardEnter.setOnClickListener { onClickEnter() }
    }

    private fun onClickNumber(index: Int) {
        priceText.text.append(index.toString())
    }

    private var isPointed: Boolean = false

    private fun onClickPoint() {
        if (isPointed) {
            return
        }
        priceText.text.append('.')
        isPointed = true
    }

    private fun onClickEnter() {
        val intent = Intent(this, GoodsPublishPageActivity::class.java)
        intent.putExtra("GoodsPrice", priceText.text.toString().toDouble())
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun onCLickBackSpace() {
        if (priceText.text.isEmpty()) {
            return
        }
        val char = priceText.text.last()
        priceText.setText(priceText.text.dropLast(1))
        if (char == '.') {
            isPointed = false
        }
    }
}
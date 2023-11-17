package com.demo.sweetfish.ui.goodsPublishPage

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sweetfish.R

class GoodsPriceSetting : AppCompatActivity() {

    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.publisher_price_setting)
        init()
    }

    private fun init() {
        editText = findViewById(R.id.editText1)
        editText.isEnabled = false
        editText.addTextChangedListener(TradeTextWatcher(editText, null))

        val mBtnkey_digits = Array<TextView>(10) {
            findViewById(this.resources.getIdentifier("btn_price_$it", "id", this.packageName))
        }

        mBtnkey_digits.forEach { it.setOnClickListener(mClickListener) }

        val mBtnKey_sk = findViewById<TextView>(R.id.btn_price_enter)
        val mBtnKey_point = findViewById<TextView>(R.id.btn_price_point)
        val mBtnKey_del = findViewById<LinearLayout>(R.id.btn_price_del)

        mBtnKey_point.setOnClickListener(mClickListener)
        mBtnKey_del.setOnClickListener(mClickListener)
        mBtnKey_sk.setOnClickListener(mClickListener)
    }

    private val mClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btn_price_1, R.id.btn_price_2, R.id.btn_price_3, R.id.btn_price_4, R.id.btn_price_5,
            R.id.btn_price_6, R.id.btn_price_7, R.id.btn_price_8, R.id.btn_price_9, R.id.btn_price_0 -> {
                val input = (view as TextView).text.toString()
                val strTmp = editText.text.toString() + input
                editText.setText(strTmp)
                editText.setTextSize(20f)
                editText.setTextColor(getColor(R.color.black))
            }
            R.id.btn_price_point -> {
                val strTmp = editText.text.toString()
                if(strTmp.indexOf(".", 0, false) == -1) {
                    val inputa = (view as TextView).text.toString()
                    val strTmp = editText.text.toString() + inputa
                    editText.setText(strTmp)
                    editText.setTextSize(20f)
                    editText.setTextColor(getColor(R.color.black))
                }
            }
            R.id.btn_price_enter -> {
                val strTmp = editText.text.toString()
                val intent = Intent(this, GoodsPublishPageActivity::class.java)
                intent.putExtra("GoodsPrice", strTmp)
                setResult(RESULT_OK, intent)
                Toast.makeText(this, "已完成设置", Toast.LENGTH_SHORT).show()
                finish()
            }
            R.id.btn_price_del -> {
                val strTmp = if (editText.text.length > 0) {
                    editText.text.toString().substring(0, editText.text.length - 1)
                } else {
                    ""
                }
                editText.setText(strTmp)
                editText.setTextSize(20f)
                editText.setTextColor(getColor(R.color.black))
            }
        }
    }

    class TradeTextWatcher(private val mEditText: EditText, private val mTextView: TextView?) : TextWatcher {

        override fun afterTextChanged(arg0: Editable?) {
            val len = mEditText.text.length
            mEditText.setSelection(len)
        }

        override fun beforeTextChanged(arg0: CharSequence?, arg1: Int, arg2: Int, arg3: Int) {}

        override fun onTextChanged(s: CharSequence?, arg1: Int, arg2: Int, arg3: Int) {}
    }


}
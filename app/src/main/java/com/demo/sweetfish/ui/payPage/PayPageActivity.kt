package com.demo.sweetfish.ui.payPage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.SweetFishApplication
import com.example.sweetfish.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class PayPageActivity : AppCompatActivity() {

    private val viewModel: PayPageActivityViewModel by lazy {
        ViewModelProvider(
            this, PayPageActivityViewModel.PayPageActivityViewModelFactory()
        )[PayPageActivityViewModel::class.java]
    }

    companion object {
        fun startActivity(appCompatActivity: AppCompatActivity, goodsId: Long) {
            val newIntent = Intent(appCompatActivity, PayPageActivity::class.java)
            newIntent.putExtra("goodsId", goodsId)
            appCompatActivity.startActivity(newIntent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_page)
        val goodsId = intent.getLongExtra("goodsId", -1L)
        if (goodsId == -1L) {
            Log.d("Mine", "GoodsId为空")
            finish()
        }
        initComponent()
        viewModel.setGoodsId(goodsId)
    }

    private fun initComponent() {
        findViewById<Button>(R.id.pay_for_goods).setOnClickListener {
            finish()
            Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show()
        }
        findViewById<TextView>(R.id.goodspaylocation).setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
            bottomSheetDialog.setContentView(
                LayoutInflater.from(SweetFishApplication.context)
                    .inflate(R.layout.location_setting, null)
            )
            val locationConfirmTextView =
                bottomSheetDialog.findViewById<TextView>(R.id.locationConfirm)!!
            val settingLocationEditText =
                bottomSheetDialog.findViewById<EditText>(R.id.settingLocation)!!
            val locationTextView = findViewById<TextView>(R.id.goodspaylocation)
            locationConfirmTextView.setOnClickListener {
                locationTextView.text = "收货地址：${settingLocationEditText.text}"
                bottomSheetDialog.dismiss()
            }
            bottomSheetDialog.show()
        }
        val goodsPayPriceTextView = findViewById<TextView>(R.id.GoodsPayPrice)
        viewModel.goodsPrice.observe(this) { price -> goodsPayPriceTextView.text = "￥$price" }
        val goodsPayTitleTextView = findViewById<TextView>(R.id.GoodsPayTitle)
        viewModel.goodsTitle.observe(this) { title -> goodsPayTitleTextView.text = title }
    }
}
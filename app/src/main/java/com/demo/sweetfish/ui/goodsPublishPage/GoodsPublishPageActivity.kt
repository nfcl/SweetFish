package com.demo.sweetfish.ui.goodsPublishPage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.SweetFishApplication
import com.example.sweetfish.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlin.concurrent.thread

class GoodsPublishPageActivity : AppCompatActivity() {

    private val viewModel: GoodsPublishPageActivityViewModel by lazy {
        ViewModelProvider(
            this, GoodsPublishPageActivityViewModel.GoodsPublishPageActivityViewModelFactory()
        )[GoodsPublishPageActivityViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goods_publish_page)
        findViewById<TextView>(R.id.GoodsPublishPageCancelButton).setOnClickListener {
            finish()
        }
        initViewModel()
        initComponent()
    }

    private fun initViewModel() {
        val priceTextView: TextView = findViewById(R.id.GoodsPublishPagePriceEditText)
        viewModel.goodsPrice.observe(this) { price -> priceTextView.text = "￥$price" }
        val locationTextView: TextView = findViewById(R.id.locationInfo)
        viewModel.goodsLocation.observe(this) { location ->
            locationTextView.text = "发货地址 : $location"
        }
    }

    private fun initComponent() {
        findViewById<TextView>(R.id.GoodsPublishPagePublishButton).setOnClickListener {
            onPublishButtonClick()
        }
        val titleEdit: EditText = findViewById(R.id.GoodsPublishPageGoodsTitleEditText)
        titleEdit.doAfterTextChanged { newTitle -> viewModel.editTitle(newTitle.toString()) }
        val infoEdit: EditText = findViewById(R.id.GoodsPublishPageGoodsInfoEditText)
        infoEdit.doAfterTextChanged { newInfo -> viewModel.editDescribe(newInfo.toString()) }
        //发货地址设定
        findViewById<TextView>(R.id.locationInfo).setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
            bottomSheetDialog.setContentView(
                LayoutInflater.from(SweetFishApplication.context)
                    .inflate(R.layout.location_setting, null)
            )
            val locationConfirmTextView =
                bottomSheetDialog.findViewById<TextView>(R.id.locationConfirm)!!
            val settingLocationEditText =
                bottomSheetDialog.findViewById<EditText>(R.id.settingLocation)!!

            locationConfirmTextView.setOnClickListener {
                viewModel.editLocation(settingLocationEditText.text.toString())
                bottomSheetDialog.hide()
            }
            bottomSheetDialog.show()
        }
        //价格设置
        findViewById<TextView>(R.id.GoodsPublishPagePriceEditText).setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
            bottomSheetDialog.setContentView(
                LayoutInflater.from(SweetFishApplication.context).inflate(
                    R.layout.activity_goods_publish_page_publisher_price_setting_page, null
                )
            )
            val priceConfirmTextView =
                bottomSheetDialog.findViewById<TextView>(R.id.GoodsPriceSettingPageKeyBoarEnterButton)!!
            val settingPriceEditText =
                bottomSheetDialog.findViewById<EditText>(R.id.GoodsPriceSettingPagePriceEditText)!!

            val keyBoardNumber = arrayOf<TextView>(
                bottomSheetDialog.findViewById(R.id.GoodsPriceSettingPageKeyBoarNumber0Button)!!,
                bottomSheetDialog.findViewById(R.id.GoodsPriceSettingPageKeyBoarNumber1Button)!!,
                bottomSheetDialog.findViewById(R.id.GoodsPriceSettingPageKeyBoarNumber2Button)!!,
                bottomSheetDialog.findViewById(R.id.GoodsPriceSettingPageKeyBoarNumber3Button)!!,
                bottomSheetDialog.findViewById(R.id.GoodsPriceSettingPageKeyBoarNumber4Button)!!,
                bottomSheetDialog.findViewById(R.id.GoodsPriceSettingPageKeyBoarNumber5Button)!!,
                bottomSheetDialog.findViewById(R.id.GoodsPriceSettingPageKeyBoarNumber6Button)!!,
                bottomSheetDialog.findViewById(R.id.GoodsPriceSettingPageKeyBoarNumber7Button)!!,
                bottomSheetDialog.findViewById(R.id.GoodsPriceSettingPageKeyBoarNumber8Button)!!,
                bottomSheetDialog.findViewById(R.id.GoodsPriceSettingPageKeyBoarNumber9Button)!!
            )

            for (number in 0..9) {
                keyBoardNumber[number].setOnClickListener { settingPriceEditText.text.append(number.toString()) }
            }

            val keyBoardPoint =
                bottomSheetDialog.findViewById<TextView>(R.id.GoodsPriceSettingPageKeyBoarPointButton)
            val keyBoardBackSpace =
                bottomSheetDialog.findViewById<LinearLayout>(R.id.GoodsPriceSettingPageKeyBoarBackSpaceButton)
            var isPointed: Boolean = false

            keyBoardPoint?.setOnClickListener {
                if (!isPointed) {
                    settingPriceEditText.text.append('.')
                    isPointed = true
                }
            }
            keyBoardBackSpace?.setOnClickListener {
                if (!settingPriceEditText.text.isEmpty()) {
                    val char = settingPriceEditText.text.last()
                    settingPriceEditText.setText(settingPriceEditText.text.dropLast(1))
                    if (char == '.') {
                        isPointed = false
                    }
                }
            }

            priceConfirmTextView.setOnClickListener {
                viewModel.editPrice(
                    settingPriceEditText.text.toString().toDouble()
                )
                bottomSheetDialog.hide()
            }
            bottomSheetDialog.show()
        }
        //商品照片设置
        val imageSelectLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val currentUri: Uri = result.data?.data!!
                thread {
                    val inputStream = contentResolver.openInputStream(currentUri)!!
                    //TODO 添加图片新的图片到待输入到数据库的新商品
                    inputStream.close()
                }
            }
        }
        findViewById<TextView>(R.id.GoodsPublishPageAddImageButton).setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            imageSelectLauncher.launch(intent)
        }
    }

    private fun onPublishButtonClick() {

        if (viewModel.goodsTitle.value!!.isEmpty()) {
            Toast.makeText(this, "请输入标题", Toast.LENGTH_SHORT).show()
        } else if (viewModel.goodsDescribe.value!!.isEmpty()) {
            Toast.makeText(this, "请输入有关商品的描述", Toast.LENGTH_SHORT).show()
        } else {
            thread {
                viewModel.publishNewGoods()
                runOnUiThread {
                    Toast.makeText(this, "商品发布成功", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }
}
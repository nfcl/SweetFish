package com.demo.sweetfish.ui.userPage.userInfoEdit

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.ui.component.RoundImageView
import com.example.sweetfish.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lljjcoder.Interface.OnCityItemClickListener
import com.lljjcoder.bean.CityBean
import com.lljjcoder.bean.DistrictBean
import com.lljjcoder.bean.ProvinceBean
import com.lljjcoder.citywheel.CityConfig
import com.lljjcoder.style.citypickerview.CityPickerView
import utils.DrawableUtils
import kotlin.concurrent.thread

class UserInfoEditPageActivity : AppCompatActivity() {

    private val viewModel: UserInfoEditPageActivityViewModel by lazy {
        ViewModelProvider(
            this, UserInfoEditPageActivityViewModel.UserInfoEditPageActivityViewModelFactory()
        )[UserInfoEditPageActivityViewModel::class.java]
    }

    private val mPicker = CityPickerView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info_edit_page)
        initComponent()
        initObserve()
    }

    private fun initObserve() {
        val avatarImageView: RoundImageView = findViewById(R.id.UserInfoEditPageAvatarImageView)
        val idTextView: TextView = findViewById(R.id.UserInfoEditPageIdTextView)
        val sexTextView: TextView = findViewById(R.id.UserInfoEditPageSexTextView)
        val nameTextView: TextView = findViewById(R.id.UserInfoEditPageNameTextView)
        val positionTextView: TextView = findViewById(R.id.UserInfoEditPagePositionTextView)
        val describeTextView: TextView = findViewById(R.id.UserInfoEditPageDescribeTextView)
        val backgroundImageView: RoundImageView =
            findViewById(R.id.UserInfoEditPageBackgroundImageView)
        viewModel.userId.observe(this) { id -> idTextView.text = id.toString() }
        viewModel.userName.observe(this) { name ->
            nameTextView.text = name ?: "填写专属昵称，更容易被大家记住"
        }
        viewModel.userSex.observe(this) { sex ->
            sexTextView.text = when (sex) {
                null -> "选择性别"
                true -> "男"
                false -> "女"
            }
        }
        viewModel.userAvatar.observe(this) { avatar -> avatarImageView.setImageDrawable(avatar) }
        viewModel.userPosition.observe(this) { position ->
            positionTextView.text = if (position == null) {
                "选择你所在的地区"
            } else {
                "${position.province} ${position.city} ${position.district}"
            }
        }
        viewModel.userDescribe.observe(this) { describe ->
            describeTextView.text = describe ?: "真诚的简介可以让买卖更有信任"
        }
        viewModel.userBackground.observe(this) { background ->
            backgroundImageView.setImageDrawable(
                background
            )
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun initComponent() {
        //返回按钮
        findViewById<ImageView>(R.id.UserInfoEditPageReturnButton).setOnClickListener {
            finish()
        }
        //名称更改
        findViewById<RelativeLayout>(R.id.UserInfoEditPageNameEditButton).setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.setContentView(
                LayoutInflater.from(SweetFishApplication.context)
                    .inflate(R.layout.activity_user_info_edit_page_name_edit_bottom_dialog, null)
            )
            val diaLogCancelButton =
                bottomSheetDialog.findViewById<TextView>(R.id.UserInfoEditPageNameEditDialogCancelButton)!!
            val diaLogConfirmButton =
                bottomSheetDialog.findViewById<TextView>(R.id.UserInfoEditPageNameEditDialogConfirmButton)!!
            val diaLogContentEditText =
                bottomSheetDialog.findViewById<TextView>(R.id.UserInfoEditPageNameEditDialogContentEditText)!!
            diaLogCancelButton.setOnClickListener {
                bottomSheetDialog.hide()
            }
            diaLogConfirmButton.setOnClickListener {
                thread {
                    viewModel.setName(diaLogContentEditText.text.toString())
                    runOnUiThread {
                        bottomSheetDialog.hide()
                    }
                }
            }
            diaLogContentEditText.text = viewModel.userName.value
            bottomSheetDialog.show()
        }
        //性别选择
        findViewById<RelativeLayout>(R.id.UserInfoEditPageSexEditButton).setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.setContentView(
                LayoutInflater.from(SweetFishApplication.context)
                    .inflate(R.layout.activity_user_info_edit_page_sex_edit_bottom_dialog, null)
            )
            val diaLogCancelButton =
                bottomSheetDialog.findViewById<TextView>(R.id.UserInfoEditPageNameEditDialogCancelButton)!!
            val diaLogConfirmButton =
                bottomSheetDialog.findViewById<TextView>(R.id.UserInfoEditPageNameEditDialogConfirmButton)!!
            val diaLogMaleTextView =
                bottomSheetDialog.findViewById<TextView>(R.id.UserInfoEditPageNameEditDialogSexMaleTextView)!!
            val diaLogFeMaleTextView =
                bottomSheetDialog.findViewById<TextView>(R.id.UserInfoEditPageNameEditDialogSexFeMaleTextView)!!
            diaLogCancelButton.setOnClickListener {
                bottomSheetDialog.hide()
            }
            diaLogConfirmButton.setOnClickListener {
                thread {
                    viewModel.setSex(viewModel.sexDialogSelect)
                    runOnUiThread {
                        bottomSheetDialog.hide()
                    }
                }
            }
            fun selectMale() {
                diaLogMaleTextView.setTextColor(getColor(R.color.UserInfoEditPageSexDialogSelectTextColor))
                diaLogFeMaleTextView.setTextColor(getColor(R.color.UserInfoEditPageSexDialogUnSelectTextColor))
                viewModel.sexDialogSelect = true
            }

            fun selectFemale() {
                diaLogMaleTextView.setTextColor(getColor(R.color.UserInfoEditPageSexDialogUnSelectTextColor))
                diaLogFeMaleTextView.setTextColor(getColor(R.color.UserInfoEditPageSexDialogSelectTextColor))
                viewModel.sexDialogSelect = false
            }
            diaLogMaleTextView.setOnClickListener {
                selectMale()
            }
            diaLogFeMaleTextView.setOnClickListener {
                selectFemale()
            }
            if (viewModel.userSex.value == null || viewModel.userSex.value == true) {
                selectMale()
            } else {
                selectFemale()
            }
            bottomSheetDialog.show()
        }
        //头像选择
        val avatarSelectLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val currentUri: Uri = result.data?.data!!
                thread {
                    val inputStream = contentResolver.openInputStream(currentUri)!!
                    viewModel.setAvatar(
                        DrawableUtils.createDrawableFromByteArray(
                            inputStream.readBytes(), ""
                        )
                    )
                    inputStream.close()
                }
            }
        }
        findViewById<RelativeLayout>(R.id.UserInfoEditPageAvatarEditButton).setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            avatarSelectLauncher.launch(intent)
        }
        //地区选择
        mPicker.init(this)
        val cityConfig = CityConfig.Builder()
            //标题
            .title("")
            //标题文字大小
            .titleTextSize(18)
            //标题文字颜色
            .titleTextColor("#585858")
            //标题栏背景色
            .titleBackgroundColor(getColor(R.color.primary).toHexString())
            //确认按钮文字颜色
            .confirTextColor("#000000")
            //确认按钮文字
            .confirmText("确定")
            //确认按钮文字大小
            .confirmTextSize(20)
            //取消按钮文字颜色
            .cancelTextColor("#000000")
            //取消按钮文字
            .cancelText("取消")
            //取消按钮文字大小
            .cancelTextSize(20)
            //显示类，只显示省份一级，显示省市两级还是显示省市区三级
            .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)
            //是否显示半透明背景
            .showBackground(true)
            //显示item的数量
            .visibleItemsCount(5)
            //默认显示的省份
            .province("浙江省")
            //默认显示省份下面的城市
            .city("杭州市")
            //默认显示省市下面的区县数据
            .district("临安区")
            //省份滚轮是否可以循环滚动
            .provinceCyclic(false)
            //城市滚轮是否可以循环滚动
            .cityCyclic(false)
            //区县滚轮是否循环滚动
            .districtCyclic(false)
            //滚轮显示模糊效果
            .drawShadows(true)
            //中间横线的颜色
            .setLineColor(getColor(R.color.primary).toHexString())
            //中间横线的高度
            .setLineHeigh(5)
            //是否显示港澳台数据，默认不显示
            .setShowGAT(true).build()
        mPicker.setConfig(cityConfig)
        mPicker.setOnCityItemClickListener(object : OnCityItemClickListener() {
            override fun onSelected(
                province: ProvinceBean?,
                city: CityBean?,
                district: DistrictBean?,
            ) {
                thread {
                    viewModel.setPosition(province.toString(), city.toString(), district.toString())
                }
            }
        })
        findViewById<RelativeLayout>(R.id.UserInfoEditPagePositionEditButton).setOnClickListener {
            mPicker.showCityPicker()
        }
        //描述更改
        findViewById<RelativeLayout>(R.id.UserInfoEditPageDescribleEditButton).setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.setContentView(
                LayoutInflater.from(SweetFishApplication.context)
                    .inflate(
                        R.layout.activity_user_info_edit_page_describe_edit_bottom_dialog,
                        null
                    )
            )
            val diaLogCancelButton =
                bottomSheetDialog.findViewById<TextView>(R.id.UserInfoEditPageNameEditDialogCancelButton)!!
            val diaLogConfirmButton =
                bottomSheetDialog.findViewById<TextView>(R.id.UserInfoEditPageNameEditDialogConfirmButton)!!
            val diaLogContentEditText =
                bottomSheetDialog.findViewById<TextView>(R.id.UserInfoEditPageNameEditDialogContentEditText)!!
            diaLogCancelButton.setOnClickListener {
                bottomSheetDialog.hide()
            }
            diaLogConfirmButton.setOnClickListener {
                thread {
                    viewModel.setDescribe(diaLogContentEditText.text.toString())
                    runOnUiThread {
                        bottomSheetDialog.hide()
                    }
                }
            }
            diaLogContentEditText.text = viewModel.userDescribe.value
            bottomSheetDialog.show()
        }
        //背景选择
        val backgroundSelectLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val currentUri: Uri = result.data?.data!!
                thread {
                    val inputStream = contentResolver.openInputStream(currentUri)!!
                    viewModel.setBackground(
                        DrawableUtils.createDrawableFromByteArray(
                            inputStream.readBytes(), ""
                        )
                    )
                    inputStream.close()
                }
            }
        }
        findViewById<RelativeLayout>(R.id.UserInfoEditPageHomePageBackgroundEditButton).setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            backgroundSelectLauncher.launch(intent)
        }
    }
}
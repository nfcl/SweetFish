package com.demo.sweetfish.ui.goodsPublishPage

import android.graphics.drawable.Drawable
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.logic.model.Goods
import com.demo.sweetfish.logic.model.GoodsPreviewImage
import com.demo.sweetfish.logic.repository.GoodsPreviewImageRepository
import com.demo.sweetfish.logic.repository.GoodsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoodsPublishPageActivityViewModel : ViewModel() {

    private val mGoodsTitle: MutableLiveData<String> = MutableLiveData("")
    private val mGoodsPrice: MutableLiveData<Double> = MutableLiveData(0.0)
    private val mGoodsDescribe: MutableLiveData<String> = MutableLiveData("")
    private val mGoodsLocation: MutableLiveData<String> = MutableLiveData("")
    private val mGoodsPreviewList: MutableLiveData<List<Drawable>> = MutableLiveData(listOf())

    val goodsTitle: LiveData<String> get() = mGoodsTitle
    val goodsPrice: LiveData<Double> get() = mGoodsPrice
    val goodsDescribe: LiveData<String> get() = mGoodsDescribe
    val goodsLocation: LiveData<String> get() = mGoodsLocation
    val goodsPreviewList: LiveData<List<Drawable>> = mGoodsPreviewList

    fun editTitle(newTitle: String) {
        mGoodsTitle.value = newTitle
    }

    fun editPrice(newPrice: Double) {
        mGoodsPrice.value = newPrice
    }

    fun editDescribe(newDescribe: String) {
        mGoodsDescribe.value = newDescribe
    }

    fun editLocation(newLocation: String) {
        mGoodsLocation.value = newLocation
    }

    @WorkerThread
    fun publishNewGoods() {
        CoroutineScope(Dispatchers.IO).launch {
            val goodsId = GoodsRepository.insert(
                Goods(
                    goodsTitle.value!!,
                    goodsPrice.value!!,
                    goodsDescribe.value!!,
                    goodsLocation.value!!,
                    SweetFishApplication.loginUserId.value!!
                )
            )
            val previewImageList = mutableListOf<GoodsPreviewImage>()
            for (image in mGoodsPreviewList.value!!) {
                previewImageList.add(GoodsPreviewImage(goodsId, image))
            }
            GoodsPreviewImageRepository.insert(previewImageList)
        }
    }

    class GoodsPublishPageActivityViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GoodsPublishPageActivityViewModel() as T
        }
    }
}
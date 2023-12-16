package com.demo.sweetfish.ui.goodsPublishPage

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.logic.model.Goods
import com.demo.sweetfish.logic.model.GoodsPreviewImage
import com.demo.sweetfish.logic.model.ImageSource
import com.demo.sweetfish.logic.repository.GoodsPreviewImageRepository
import com.demo.sweetfish.logic.repository.GoodsRepository
import com.demo.sweetfish.logic.repository.ImageSourceRepository
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
    val goodsPreviewList: LiveData<List<Drawable>> get() = mGoodsPreviewList

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

    fun addImageToList(image: Drawable) {
        val currentList: MutableList<Drawable> =
            (mGoodsPreviewList.value ?: listOf()).toMutableList()
        currentList.add(image)
        mGoodsPreviewList.value = currentList
    }

    fun removeImageAt(position: Int) {
        val currentList = mGoodsPreviewList.value?.toMutableList() ?: mutableListOf()
        if (position in 0 until currentList.size) {
            currentList.removeAt(position)
            mGoodsPreviewList.value = currentList
        }
    }

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
                previewImageList.add(
                    GoodsPreviewImage(
                        goodsId, ImageSourceRepository.insert(
                            ImageSource(image)
                        )
                    )
                )
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
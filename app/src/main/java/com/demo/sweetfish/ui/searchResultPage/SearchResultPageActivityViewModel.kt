package com.demo.sweetfish.ui.searchResultPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.AppDatabase
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.logic.model.GoodsWithSellerInfo
import kotlin.concurrent.thread

class SearchResultPageActivityViewModel : ViewModel() {

    private val _goodsList: MutableLiveData<List<GoodsWithSellerInfo>> =
        MutableLiveData<List<GoodsWithSellerInfo>>()

    val goodsList: LiveData<List<GoodsWithSellerInfo>>
        get() = _goodsList

    fun refreshGoodsList(searchContent: String) {
        thread {
            val goodsWithSellerInfoDao =
                AppDatabase.getDatabase(SweetFishApplication.context).goodsWithSellerInfoDao()
            _goodsList.postValue(goodsWithSellerInfoDao.findLikeGoodsName(searchContent))
        }
    }

    class SearchResultPageActivityViewModelFactory() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchResultPageActivityViewModel() as T
        }
    }
}
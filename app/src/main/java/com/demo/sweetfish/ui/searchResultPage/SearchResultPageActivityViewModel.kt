package com.demo.sweetfish.ui.searchResultPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.AppDatabase
import com.demo.sweetfish.logic.model.GoodsWithSellerInfo
import com.demo.sweetfish.logic.model.SearchResultPageUserInfo

class SearchResultPageActivityViewModel : ViewModel() {

    private val searchContent: MutableLiveData<String> = MutableLiveData()

    val goodsList: LiveData<List<GoodsWithSellerInfo>> = Transformations.switchMap(searchContent) {
        AppDatabase.getDatabase().goodsWithSellerInfoDao().findLikeGoodsNameReturnLiveData(it)
    }

    val userList: LiveData<List<SearchResultPageUserInfo>> =
        Transformations.switchMap(searchContent) {
            AppDatabase.getDatabase().searchResultPageUserInfoDao()
                .findLikeUserNameReturnLiveData(it)
        }

    fun setSearchContent(content: String) {
        searchContent.value = content
    }

    class SearchResultPageActivityViewModelFactory() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchResultPageActivityViewModel() as T
        }
    }
}
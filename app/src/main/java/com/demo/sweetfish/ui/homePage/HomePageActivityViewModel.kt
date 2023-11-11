package com.demo.sweetfish.ui.homePage

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.AppDatabase
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.logic.model.GoodsWithSellerInfo
import kotlin.concurrent.thread

class HomePageActivityViewModel : ViewModel() {

    private val _goodsList: MutableLiveData<List<GoodsWithSellerInfo>> =
        MutableLiveData<List<GoodsWithSellerInfo>>()

    val goodsList: LiveData<List<GoodsWithSellerInfo>>
        get() = _goodsList

    val userAvatar: LiveData<Drawable> =
        Transformations.map(SweetFishApplication.loginUser) { userInfo ->
            userInfo.avatarPic
        }

    val userName: LiveData<String> =
        Transformations.map(SweetFishApplication.loginUser) { userInfo ->
            userInfo.name ?: userInfo.id.toString()
        }
    val userFollowNum: LiveData<Int> =
        Transformations.switchMap(SweetFishApplication.loginUser) { userInfo ->
            AppDatabase.getDatabase().userFollowDao().getFollowsNumReturnLiveData(userInfo.id)
        }
    val userFanNum: LiveData<Int> =
        Transformations.switchMap(SweetFishApplication.loginUser) { userInfo ->
            AppDatabase.getDatabase().userFollowDao().getFansNumReturnLiveData(userInfo.id)
        }


    fun refreshGoodsList() {
        thread {
            val goodsWithSellerInfoDao =
                AppDatabase.getDatabase(SweetFishApplication.context).goodsWithSellerInfoDao()
            _goodsList.postValue(goodsWithSellerInfoDao.findAll())
        }
    }

    class HomePageActivityViewModelFactory() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomePageActivityViewModel() as T
        }
    }
}


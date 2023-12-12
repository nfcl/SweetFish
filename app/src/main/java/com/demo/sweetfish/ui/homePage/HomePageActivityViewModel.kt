package com.demo.sweetfish.ui.homePage

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.logic.model.GoodsWithSellerInfo
import com.demo.sweetfish.logic.model.ImageSource
import com.demo.sweetfish.logic.repository.GoodsWithSellerInfoRepository
import com.demo.sweetfish.logic.repository.ImageSourceRepository
import com.demo.sweetfish.logic.repository.UserFollowRepository

class HomePageActivityViewModel : ViewModel() {

    val userAvatar: LiveData<ImageSource> =
        Transformations.switchMap(SweetFishApplication.loginUser) { userInfo ->
            ImageSourceRepository.findById(userInfo.avatar)
        }

    val userName: LiveData<String> =
        Transformations.map(SweetFishApplication.loginUser) { userInfo ->
            userInfo.name ?: userInfo.id.toString()
        }
    val userFollowNum: LiveData<Long> =
        Transformations.switchMap(SweetFishApplication.loginUser) { userInfo ->
            UserFollowRepository.getFollowNum(userInfo.id)
        }
    val userFanNum: LiveData<Long> =
        Transformations.switchMap(SweetFishApplication.loginUser) { userInfo ->
            UserFollowRepository.getFanNum(userInfo.id)
        }

    fun getGoodsList(): LiveData<List<GoodsWithSellerInfo>> {
        return GoodsWithSellerInfoRepository.findAll()
    }

    class HomePageActivityViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomePageActivityViewModel() as T
        }
    }
}


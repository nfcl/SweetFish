package com.demo.sweetfish.ui.goodsPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.logic.model.Goods
import com.demo.sweetfish.logic.model.ImageSource
import com.demo.sweetfish.logic.model.User
import com.demo.sweetfish.logic.model.UserFollow
import com.demo.sweetfish.logic.repository.GoodsPreviewImageRepository
import com.demo.sweetfish.logic.repository.GoodsRepository
import com.demo.sweetfish.logic.repository.ImageSourceRepository
import com.demo.sweetfish.logic.repository.UserFollowRepository
import com.demo.sweetfish.logic.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoodsPageActivityViewModel : ViewModel() {

    private val mGoodsId: MutableLiveData<Long> = MutableLiveData(-1)

    private val goodsInfo: LiveData<Goods> = Transformations.switchMap(mGoodsId) { id ->
        GoodsRepository.findByGoodsId(id)
    }

    private val userInfo: LiveData<User> = Transformations.switchMap(goodsInfo) { info ->
        UserRepository.findUserById(info.sellerId)
    }

    val goodsPrice: LiveData<Double> = Transformations.map(goodsInfo) { info ->
        info.price
    }

    val goodsDescribe: LiveData<String> = Transformations.map(goodsInfo) { info ->
        info.describe
    }

    val goodsPreviewImage: LiveData<List<Long>> = Transformations.switchMap(mGoodsId) { id ->
        GoodsPreviewImageRepository.findContentByGoodsId(id)
    }

    val sellerId: LiveData<Long> = Transformations.map(userInfo) { info ->
        info.id
    }

    val sellerName: LiveData<String> = Transformations.map(userInfo) { info ->
        info.name ?: info.id.toString()
    }

    val sellerAvatar: LiveData<ImageSource> = Transformations.switchMap(userInfo) { info ->
        ImageSourceRepository.findById(info.avatar)
    }

    val isFollowed: LiveData<Boolean> = Transformations.switchMap(userInfo) { info ->
        UserFollowRepository.isFollowed(
            UserFollow(
                info.id, SweetFishApplication.loginUserId.value!!
            )
        )
    }

    fun setGoodsId(goodsId: Long) {
        mGoodsId.value = goodsId
    }

    fun setFollowState(isFollow: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            val followId = sellerId.value
            val fanId = SweetFishApplication.loginUserId.value
            if (followId == null || fanId == null) {
                return@launch
            }
            if (isFollow) {
                UserFollowRepository.insert(UserFollow(followId, fanId))
            } else {
                UserFollowRepository.delete(UserFollow(followId, fanId))
            }
            forceRefresh()
        }
    }

    fun messageSeller() {

    }

    fun wantGoods() {

    }

    private fun forceRefresh() {
        mGoodsId.postValue(mGoodsId.value)
    }

    class GoodsPageActivityViewModelFactory() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GoodsPageActivityViewModel() as T
        }
    }
}
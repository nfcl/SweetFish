package com.demo.sweetfish.ui.goodsPublishPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.logic.model.Goods
import com.demo.sweetfish.logic.model.GoodsWithSellerInfo
import com.demo.sweetfish.logic.model.User
import com.demo.sweetfish.logic.repository.GoodsRepository
import com.demo.sweetfish.logic.repository.GoodsWithSellerInfoRepository
import com.demo.sweetfish.logic.repository.UserRepository

class MyPublishActivityViewModel : ViewModel() {

    enum class PublishState(val parseNum:Long) {
        //TODO 购买状态的几种类型的值 如 发货(Post),已到(Arrived)....
        Sold(0b0),
        Paper(0b1),
        Down(0b10)
    }
    private val _userId: MutableLiveData<Long> = MutableLiveData()
    private val mGoodsId: MutableLiveData<Long> = MutableLiveData(-1)

    private val goodsInfo: LiveData<Goods> = Transformations.switchMap(mGoodsId) { id ->
        GoodsRepository.findByGoodsId(id)
    }
    private val userInfo: LiveData<User> = Transformations.switchMap(goodsInfo) { info ->
        UserRepository.findUserById(info.sellerId)
    }
    //在Activity中Observe这个变量，当这个变量改变后就会自动更改Activity中的列表内容
    fun getGoodsList(): LiveData<List<GoodsWithSellerInfo>> {

        return GoodsWithSellerInfoRepository.findBySellerId(SweetFishApplication.loginUserId.value!!)
    }

    class MyPublishPageActivityViewModelFactory():ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MyPublishActivityViewModel() as T
        }
    }
}
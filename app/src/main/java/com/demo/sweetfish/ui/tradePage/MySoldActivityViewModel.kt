package com.demo.sweetfish.ui.goodsPublishPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.logic.model.GoodsBuyInfo

class MySoldActivityViewModel : ViewModel() {

    enum class SoldState(val parseNum:Long) {
        //TODO 购买状态的几种类型的值 如 发货(Post),已到(Arrived)....
        All(0b0),
        Pay(0b1),
        Send(0b10),
        Tui(0b100),
        Shou(0b1000),
        Ping(0b10000);
    }

    private val mSearchSoldState: MutableLiveData<SoldState> = MutableLiveData()

    val searchBuyState: LiveData<SoldState>
        get() = mSearchSoldState

    //在Activity中Observe这个变量，当这个变量改变后就会自动更改Activity中的列表内容
    val buyList: LiveData<GoodsBuyInfo> = Transformations.switchMap(mSearchSoldState) {
        null
//        state ->
//        if (state == SoldState.All){
//
//        }
    }

    //TODO 一个更改mSearchBuyState的方法用于更改查看的购买状态的商品
    fun setSearchBuyState() {
//        mSearchSoldState.value = state
    }

    class MySoldPageActivityViewModelFactory():ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MySoldActivityViewModel() as T
        }
    }
}
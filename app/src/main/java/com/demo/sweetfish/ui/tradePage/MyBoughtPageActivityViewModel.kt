package com.demo.sweetfish.ui.tradePage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.logic.model.GoodsBuyInfo
import com.demo.sweetfish.logic.model.GoodsState
import com.demo.sweetfish.logic.repository.GoodsBuyInfoRepository

class MyBoughtPageActivityViewModel : ViewModel() {

    enum class BuyState(val parseNum: Int) {
        All(-1),    //全部
        Pay(0),     //待付款
        Send(1),    //代发货
        Shou(2),    //待收货
        Ping(3),    //待评价
        Tui(4);     //退款中
    }

    private val mSearchBuyState: MutableLiveData<BuyState> = MutableLiveData()

    val searchBuyState: LiveData<BuyState>
        get() = mSearchBuyState

    val buyList: LiveData<List<GoodsBuyInfo>> =
        Transformations.switchMap(mSearchBuyState) { state ->
            if (state == BuyState.All) {
                GoodsBuyInfoRepository.findBoughtByBuyerId()
            } else {
                GoodsBuyInfoRepository.findBoughtByGoodsState(GoodsState.fromParseNum(state.parseNum))
            }
        }

    fun setSearchBuyState(state: BuyState) {
        mSearchBuyState.value = state
    }

    class MyBoughtPageActivityViewModelFactory() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MyBoughtPageActivityViewModel() as T
        }
    }
}
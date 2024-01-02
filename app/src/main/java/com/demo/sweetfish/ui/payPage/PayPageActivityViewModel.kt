package com.demo.sweetfish.ui.payPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.logic.model.Goods
import com.demo.sweetfish.logic.repository.GoodsRepository

class PayPageActivityViewModel : ViewModel() {

    private val mGoodsId: MutableLiveData<Long> = MutableLiveData(-1)

    private val goodsInfo: LiveData<Goods> = Transformations.switchMap(mGoodsId) { id ->
        GoodsRepository.findByGoodsId(id)
    }
    val goodsPrice: LiveData<Double> = Transformations.map(goodsInfo) { info ->
        info.price
    }
    val goodsTitle: LiveData<String> = Transformations.map(goodsInfo) { info ->
        info.title
    }

    fun setGoodsId(goodsId: Long) {
        mGoodsId.value = goodsId
    }

    class PayPageActivityViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PayPageActivityViewModel() as T
        }
    }
}
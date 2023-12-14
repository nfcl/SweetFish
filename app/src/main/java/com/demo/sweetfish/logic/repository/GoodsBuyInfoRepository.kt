package com.demo.sweetfish.logic.repository

import androidx.lifecycle.liveData
import com.demo.sweetfish.NetWorkService
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.logic.model.GoodsState
import kotlinx.coroutines.Dispatchers
import retrofit2.await

object GoodsBuyInfoRepository {

    fun findPublishedByBuyerId(sellerId: Long) = liveData(Dispatchers.IO) {
        emit(NetWorkService.goodsBuyInfoService().findPublishedByBuyerId(sellerId).await())
    }

    fun findBoughtByBuyerId(buyerId: Long = SweetFishApplication.loginUserId.value!!) =
        liveData(Dispatchers.IO) {
            emit(NetWorkService.goodsBuyInfoService().findBoughtByBuyerId(buyerId).await())
        }

    fun findBoughtByGoodsState(
        state: GoodsState,
        buyerId: Long = SweetFishApplication.loginUserId.value!!,
    ) = liveData(Dispatchers.IO) {
        emit(
            NetWorkService.goodsBuyInfoService().findBoughtByGoodsState(state.parseNum, buyerId)
                .await()
        )
    }

    fun findSoldBySellerId(sellerId: Long = SweetFishApplication.loginUserId.value!!) =
        liveData(Dispatchers.IO) {
            emit(NetWorkService.goodsBuyInfoService().findSoldBySellerId(sellerId).await())
        }

    fun findSoldByGoodsState(
        state: GoodsState,
        sellerId: Long = SweetFishApplication.loginUserId.value!!,
    ) = liveData(Dispatchers.IO) {
        emit(
            NetWorkService.goodsBuyInfoService().findSoldByGoodsState(state.parseNum, sellerId)
                .await()
        )
    }

}
package com.demo.sweetfish.logic.repository

import androidx.lifecycle.liveData
import com.demo.sweetfish.NetWorkService
import com.demo.sweetfish.logic.model.Goods
import kotlinx.coroutines.Dispatchers
import retrofit2.await

object GoodsRepository {

    suspend fun insert(goods: Goods): Long {
        return NetWorkService.goodsService().insert(goods).await()
    }

    suspend fun update(goods: Goods) {
        NetWorkService.goodsService().update(goods).await()
    }

    fun findAll() = liveData(Dispatchers.IO) {
        emit(NetWorkService.goodsService().findAll().await())
    }

    fun findByGoodsId(goodsId: Long) = liveData(Dispatchers.IO) {
        emit(NetWorkService.goodsService().findByGoodsId(goodsId).await())
    }

    fun findBySellerId(sellerId: Long) = liveData(Dispatchers.IO) {
        emit(NetWorkService.goodsService().findBySellerId(sellerId).await())
    }

    fun findLikeGoodsTitle(goodsTitle: String) = liveData(Dispatchers.IO) {
        emit(NetWorkService.goodsService().findLikeGoodsTitle(goodsTitle).await())
    }

    suspend fun delete(goods: Goods) {
        NetWorkService.goodsService().delete(goods).await()
    }

}
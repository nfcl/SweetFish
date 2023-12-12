package com.demo.sweetfish.logic.repository

import androidx.lifecycle.liveData
import com.demo.sweetfish.NetWorkService
import kotlinx.coroutines.Dispatchers
import retrofit2.await

object GoodsWithSellerInfoRepository {

    fun findAll() = liveData(Dispatchers.IO) {
        emit(
            try {
                NetWorkService.goodsWithSellerInfoService().findAll().await()
            } catch (e: Exception) {
                e.printStackTrace()
                listOf()
            }
        )
    }

    fun findLikeGoodsTitle(goodsTitle: String) = liveData(Dispatchers.IO) {
        emit(
            try {
                NetWorkService.goodsWithSellerInfoService().findLikeGoodsTitle(goodsTitle).await()
            } catch (e: Exception) {
                e.printStackTrace()
                listOf()
            }
        )
    }

}
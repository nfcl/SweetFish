package com.demo.sweetfish.logic.repository

import androidx.lifecycle.liveData
import com.demo.sweetfish.NetWorkService
import com.demo.sweetfish.logic.model.GoodsPreviewImage
import kotlinx.coroutines.Dispatchers
import retrofit2.await

object GoodsPreviewImageRepository {

    suspend fun insert(goodsPreviewImage: List<GoodsPreviewImage>) {
        NetWorkService.goodsPreviewImageService().insert(goodsPreviewImage).await()
    }

    fun findContentByGoodsId(goodsId: Long) = liveData(Dispatchers.IO) {
        emit(NetWorkService.goodsPreviewImageService().findContentByGoodsId(goodsId).await())
    }

    suspend fun delete(goodsPreviewImage: GoodsPreviewImage) {
        NetWorkService.goodsPreviewImageService().delete(goodsPreviewImage).await()
    }

}
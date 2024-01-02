package com.demo.sweetfish.logic.repository

import androidx.lifecycle.liveData
import com.demo.sweetfish.NetWorkService
import kotlinx.coroutines.Dispatchers
import retrofit2.await

object MessageInfoRepository {

    fun findByUserId(userId: Long) = liveData(Dispatchers.IO) {
        emit(NetWorkService.messageInfoService().findByUserId(userId).await())
    }

}
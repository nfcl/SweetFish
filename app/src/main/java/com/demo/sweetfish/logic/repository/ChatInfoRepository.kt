package com.demo.sweetfish.logic.repository

import androidx.lifecycle.liveData
import com.demo.sweetfish.NetWorkService
import kotlinx.coroutines.Dispatchers
import retrofit2.await

object ChatInfoRepository {

    fun findByChatId(chatId: Long) = liveData(Dispatchers.IO) {
        emit(NetWorkService.chatInfoService().findByChatId(chatId).await())
    }

}
package com.demo.sweetfish.logic.repository

import androidx.lifecycle.liveData
import com.demo.sweetfish.NetWorkService
import com.demo.sweetfish.logic.model.Chat
import com.demo.sweetfish.logic.model.Goods
import kotlinx.coroutines.Dispatchers
import retrofit2.await

object ChatRepository {

    suspend fun insert(chatInfo: Chat): Long {
        return NetWorkService.chatService().insert(chatInfo).await()
    }

    suspend fun update(chatInfo: Chat) {
        NetWorkService.chatService().update(chatInfo).await()
    }

    fun findAll() = liveData(Dispatchers.IO) {
        emit(NetWorkService.chatService().findAll().await())
    }

    suspend fun delete(goods: Goods) {
        NetWorkService.goodsService().delete(goods).await()
    }


    suspend fun isExisted(chatInfo: Chat): Long {
        return NetWorkService.chatService().isExisted(chatInfo).await()
    }
}
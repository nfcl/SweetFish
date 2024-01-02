package com.demo.sweetfish.logic.repository

import androidx.lifecycle.liveData
import com.demo.sweetfish.NetWorkService
import com.demo.sweetfish.logic.model.Message
import kotlinx.coroutines.Dispatchers
import retrofit2.await

object MessageRepository {

    suspend fun insert(message: Message): Long {
        return NetWorkService.messageService().insert(message).await()
    }

    suspend fun update(message: Message) {
        NetWorkService.messageService().update(message).await()
    }

    fun findAll() = liveData(Dispatchers.IO) {
        emit(NetWorkService.messageService().findAll().await())
    }

    fun findByChatId(chatId: Long) = liveData(Dispatchers.IO) {
        emit(NetWorkService.messageService().findByChatId(chatId).await())
    }

    fun findBySenderId(senderId: Long) = liveData(Dispatchers.IO) {
        emit(NetWorkService.chatService().findByUserId(senderId).await())
    }

    suspend fun delete(message: Message) {
        NetWorkService.messageService().delete(message).await()
    }

}
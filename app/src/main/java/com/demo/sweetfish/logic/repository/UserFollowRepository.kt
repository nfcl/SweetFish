package com.demo.sweetfish.logic.repository

import androidx.lifecycle.liveData
import com.demo.sweetfish.NetWorkService
import com.demo.sweetfish.logic.model.UserFollow
import kotlinx.coroutines.Dispatchers
import retrofit2.await

object UserFollowRepository {

    suspend fun insert(userFollow: UserFollow) {
        NetWorkService.userFollowService().insert(userFollow).await()
    }

    suspend fun delete(userFollow: UserFollow) {
        NetWorkService.userFollowService().delete(userFollow).await()
    }

    fun findByFanId(fanId: Long) = liveData(Dispatchers.IO) {
        emit(NetWorkService.userFollowService().findByFanId(fanId).await())
    }

    fun findByFollowId(followId: Long) = liveData(Dispatchers.IO) {
        emit(NetWorkService.userFollowService().findByFollowId(followId).await())
    }

    fun getFollowNum(fanId: Long) = liveData(Dispatchers.IO) {
        emit(NetWorkService.userFollowService().getFollowNum(fanId).await())
    }

    fun getFanNum(followId: Long) = liveData(Dispatchers.IO) {
        emit(NetWorkService.userFollowService().getFanNum(followId).await())
    }

    fun isFollowed(userFollow: UserFollow) = liveData(Dispatchers.IO) {
        emit(NetWorkService.userFollowService().isFollowed(userFollow).await())
    }

}
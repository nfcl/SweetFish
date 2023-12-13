package com.demo.sweetfish.logic.repository

import androidx.lifecycle.liveData
import com.demo.sweetfish.NetWorkService
import kotlinx.coroutines.Dispatchers
import retrofit2.await

object UserWithFollowInfoRepository {

    fun getFollowUser(fanId: Long, loginId: Long) = liveData(Dispatchers.IO) {
        emit(NetWorkService.userWithFollowInfoService().getFollowUser(fanId, loginId).await())
    }

    fun getFanUser(followId: Long, loginId: Long) = liveData(Dispatchers.IO) {
        emit(NetWorkService.userWithFollowInfoService().getFanUser(followId, loginId).await())
    }

}
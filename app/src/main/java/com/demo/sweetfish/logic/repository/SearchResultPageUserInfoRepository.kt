package com.demo.sweetfish.logic.repository

import androidx.lifecycle.liveData
import com.demo.sweetfish.NetWorkService
import kotlinx.coroutines.Dispatchers
import retrofit2.await

object SearchResultPageUserInfoRepository {

    fun findLikeUserName(userName: String) = liveData(Dispatchers.IO) {
        emit(NetWorkService.searchResultPageUserInfoService().findLikeUserName(userName).await())
    }

}
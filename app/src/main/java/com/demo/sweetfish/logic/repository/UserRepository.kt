package com.demo.sweetfish.logic.repository

import androidx.lifecycle.liveData
import com.demo.sweetfish.NetWorkService
import com.demo.sweetfish.logic.model.User
import kotlinx.coroutines.Dispatchers
import retrofit2.await

object UserRepository {

    fun register(newUserInfo: User) = liveData(Dispatchers.IO) {
        emit(
            try {
                if (NetWorkService.userService().checkDuplicateAccount(newUserInfo.account)
                        .await()
                ) {
                    -2
                } else {
                    NetWorkService.userService().insert(newUserInfo).await()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                -1
            }
        )
    }

    fun login(account: String, password: String) = liveData(Dispatchers.IO) {
        emit(
            try {
                NetWorkService.userService().findByAccountAndPassword(account, password).await()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        )
    }

    suspend fun updateUserInfo(newUserInfo: User) {
        NetWorkService.userService().update(newUserInfo).await()
    }

    fun findUserById(id: Long) = liveData(Dispatchers.IO) {
        emit(
            try {
                NetWorkService.userService().findById(id).await()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        )
    }

    fun findUserLikeName(name: String) = liveData(Dispatchers.IO) {
        emit(
            try {
                NetWorkService.userService().findLikeName(name).await()
            } catch (e: Exception) {
                e.printStackTrace()
                listOf()
            }
        )
    }

}
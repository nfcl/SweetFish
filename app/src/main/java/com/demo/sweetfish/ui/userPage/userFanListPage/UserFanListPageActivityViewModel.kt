package com.demo.sweetfish.ui.userPage.userFanListPage

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.AppDatabase
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.logic.model.UserFollow
import com.demo.sweetfish.logic.model.UserWithFollowInfo
import com.demo.sweetfish.logic.repository.UserFollowRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserFanListPageActivityViewModel : ViewModel() {

    private val _userId: MutableLiveData<Long> = MutableLiveData()

    val followList: LiveData<List<UserWithFollowInfo>> = Transformations.switchMap(_userId) { id ->
        AppDatabase.getDatabase().userWithFollowInfoDao()
            .getFanUserReturnLivData(id, SweetFishApplication.loginUserId.value!!)
    }

    fun setUserId(userId: Long) {
        _userId.value = userId
    }

    @WorkerThread
    fun followUser(userId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            UserFollowRepository.insert(
                UserFollow(
                    userId, SweetFishApplication.loginUserId.value!!
                )
            )
            forceToRefresh()
        }
    }

    @WorkerThread
    fun unFollowUser(userId: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            UserFollowRepository.delete(
                UserFollow(
                    userId, SweetFishApplication.loginUserId.value!!
                )
            )
            forceToRefresh()
        }
    }

    @WorkerThread
    private fun forceToRefresh() {
        _userId.postValue(_userId.value)
    }

    class UserFanListPageActivityViewModelFactory() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserFanListPageActivityViewModel() as T
        }
    }
}
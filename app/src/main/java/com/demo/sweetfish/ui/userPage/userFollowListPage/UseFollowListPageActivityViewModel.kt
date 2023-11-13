package com.demo.sweetfish.ui.userPage.userFollowListPage

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

class UseFollowListPageActivityViewModel : ViewModel() {

    private val _userId: MutableLiveData<Long> = MutableLiveData()

    val followList: LiveData<List<UserWithFollowInfo>> = Transformations.switchMap(_userId) { id ->
        AppDatabase.getDatabase().userWithFollowInfoDao()
            .getFollowUserReturnLivData(id, SweetFishApplication.loginUserId.value!!)
    }

    fun setUserId(userId: Long) {
        _userId.value = userId
    }

    @WorkerThread
    fun followUser(userId: Long) {
        AppDatabase.getDatabase().userFollowDao()
            .insert(UserFollow(userId, SweetFishApplication.loginUserId.value!!))
        forceToRefresh()
    }

    @WorkerThread
    fun unFollowUser(userId: Long) {
        AppDatabase.getDatabase().userFollowDao()
            .delete(UserFollow(userId, SweetFishApplication.loginUserId.value!!))
        forceToRefresh()
    }

    @WorkerThread
    private fun forceToRefresh() {
        _userId.postValue(_userId.value)
    }

    class UseFollowListPageActivityViewModelFactory() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UseFollowListPageActivityViewModel() as T
        }
    }

}
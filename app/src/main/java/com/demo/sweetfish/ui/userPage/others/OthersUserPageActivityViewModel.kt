package com.demo.sweetfish.ui.userPage.others

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.AppDatabase
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.logic.model.ImageSource
import com.demo.sweetfish.logic.model.User
import com.demo.sweetfish.logic.model.UserFollow
import com.demo.sweetfish.logic.repository.ImageSourceRepository
import com.demo.sweetfish.logic.repository.UserRepository

class OthersUserPageActivityViewModel : ViewModel() {

    private val _userId: MutableLiveData<Long> = MutableLiveData()

    private val userInfo: LiveData<User> = Transformations.switchMap(_userId) { id ->
        UserRepository.findUserById(id)
    }

    val userId: LiveData<Long>
        get() = _userId

    val userName: LiveData<String?> = Transformations.map(userInfo) { info ->
        info.name
    }
    val userAvatar: LiveData<ImageSource> = Transformations.switchMap(userInfo) { info ->
        ImageSourceRepository.findById(info.avatar)
    }
    val userBackground: LiveData<ImageSource> = Transformations.switchMap(userInfo) { info ->
        ImageSourceRepository.findById(info.background)
    }
    val userSex: LiveData<Boolean?> = Transformations.map(userInfo) { info ->
        info.sex
    }
    val userDescribe: LiveData<String?> = Transformations.map(userInfo) { info ->
        info.describe
    }
    val userFollowNum: LiveData<Int> = Transformations.switchMap(_userId) { id ->
        AppDatabase.getDatabase().userFollowDao().getFollowsNumReturnLiveData(id)
    }
    val userFanNum: LiveData<Int> = Transformations.switchMap(_userId) { id ->
        AppDatabase.getDatabase().userFollowDao().getFansNumReturnLiveData(id)
    }
    val isFollowed: LiveData<Int> = Transformations.switchMap(_userId) { id ->
        AppDatabase.getDatabase().userFollowDao()
            .isFollowed(id, SweetFishApplication.loginUserId.value!!)
    }

    @WorkerThread
    fun setUserId(id: Long) {
        _userId.postValue(id)
    }

    @WorkerThread
    fun followUser() {
        val followId = _userId.value
        val fanId = SweetFishApplication.loginUserId.value
        if (followId == null || fanId == null) {
            return
        }
        AppDatabase.getDatabase().userFollowDao().insert(UserFollow(followId, fanId))
        forceToRefresh()
    }

    @WorkerThread
    fun unFollowUser() {
        val followId = _userId.value
        val fanId = SweetFishApplication.loginUserId.value
        if (followId == null || fanId == null) {
            return
        }
        AppDatabase.getDatabase().userFollowDao().delete(UserFollow(followId, fanId))
        forceToRefresh()
    }

    private fun forceToRefresh() {
        _userId.postValue(_userId.value)
    }

    class OthersUserPageActivityViewModelFactory() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return OthersUserPageActivityViewModel() as T
        }
    }
}
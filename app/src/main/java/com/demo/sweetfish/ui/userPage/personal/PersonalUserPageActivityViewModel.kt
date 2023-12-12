package com.demo.sweetfish.ui.userPage.personal

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.AppDatabase
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.logic.model.ImageSource
import com.demo.sweetfish.logic.repository.ImageSourceRepository

class PersonalUserPageActivityViewModel : ViewModel() {

    val userId: LiveData<Long> = Transformations.map(SweetFishApplication.loginUser) {
        it.id
    }
    val userName: LiveData<String?> = Transformations.map(SweetFishApplication.loginUser) {
        it.name
    }
    val userAvatar: LiveData<ImageSource> =
        Transformations.switchMap(SweetFishApplication.loginUser) {
            ImageSourceRepository.findById(it.avatar)
        }
    val userBackground: LiveData<ImageSource> =
        Transformations.switchMap(SweetFishApplication.loginUser) {
            ImageSourceRepository.findById(it.background)
        }
    val userSex: LiveData<Boolean?> = Transformations.map(SweetFishApplication.loginUser) {
        it.sex
    }
    val userDescribe: LiveData<String?> = Transformations.map(SweetFishApplication.loginUser) {
        it.describe
    }
    val userFollowNum: LiveData<Int> = Transformations.switchMap(userId) { id ->
        AppDatabase.getDatabase().userFollowDao().getFollowsNumReturnLiveData(id)
    }
    val userFanNum: LiveData<Int> = Transformations.switchMap(userId) { id ->
        AppDatabase.getDatabase().userFollowDao().getFansNumReturnLiveData(id)
    }

    class PersonalUserPageActivityViewModelFactory() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PersonalUserPageActivityViewModel() as T
        }
    }
}
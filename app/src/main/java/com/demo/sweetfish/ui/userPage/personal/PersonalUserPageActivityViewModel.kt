package com.demo.sweetfish.ui.userPage.personal

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.SweetFishApplication

class PersonalUserPageActivityViewModel : ViewModel() {

    val userId: LiveData<Long> = Transformations.map(SweetFishApplication.loginUser) {
        it.id
    }
    val userName: LiveData<String?> = Transformations.map(SweetFishApplication.loginUser) {
        it.name
    }
    val userAvatar: LiveData<Drawable> = Transformations.map(SweetFishApplication.loginUser) {
        it.avatarPic
    }
    val userBackground: LiveData<Drawable> = Transformations.map(SweetFishApplication.loginUser) {
        it.background
    }
    val userSex: LiveData<Boolean?> = Transformations.map(SweetFishApplication.loginUser) {
        it.sex
    }
    val userDescribe: LiveData<String?> = Transformations.map(SweetFishApplication.loginUser) {
        it.describe
    }

    class UserPageActivityViewModelFactory() : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PersonalUserPageActivityViewModel() as T
        }
    }
}
package com.demo.sweetfish.ui.userPage.userInfoEdit

import android.graphics.drawable.Drawable
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.AppDatabase
import com.demo.sweetfish.SweetFishApplication

class UserInfoEditPageActivityViewModel : ViewModel() {

    val userId: LiveData<String> =
        Transformations.map(SweetFishApplication.loginUser) {
            it.id.toString()
        }
    val userName: LiveData<String> =
        Transformations.map(SweetFishApplication.loginUser) {
            it.name ?: "填写专属昵称，更容易被大家记住"
        }
    val userSex: LiveData<String> =
        Transformations.map(SweetFishApplication.loginUser) {
            when (it.sex) {
                null -> "选择性别"
                true -> "男"
                false -> "女"
            }
        }
    val userAvatar: LiveData<Drawable> =
        Transformations.map(SweetFishApplication.loginUser) {
            it.avatarPic
        }

    @WorkerThread
    fun setAvatar(drawable: Drawable) {
        val newUserInfo = SweetFishApplication.loginUser.value!!
        newUserInfo.avatarPic = drawable
        val userDao = AppDatabase.getDatabase().userDao()
        userDao.update(newUserInfo)
        SweetFishApplication.forceRefreshUserInfo()
    }

    class UserInfoEditPageActivityViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserInfoEditPageActivityViewModel() as T
        }
    }
}
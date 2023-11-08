package com.demo.sweetfish.ui.userPage.userInfoEdit

import android.graphics.drawable.Drawable
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.AppDatabase
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.logic.model.UserPositionInfo

class UserInfoEditPageActivityViewModel : ViewModel() {

    val userId: LiveData<Long> = Transformations.map(SweetFishApplication.loginUser) {
        it.id
    }
    val userName: LiveData<String> = Transformations.map(SweetFishApplication.loginUser) {
        it.name ?: "填写专属昵称，更容易被大家记住"
    }
    val userSex: LiveData<Boolean?> = Transformations.map(SweetFishApplication.loginUser) {
        it.sex
    }
    val userAvatar: LiveData<Drawable> = Transformations.map(SweetFishApplication.loginUser) {
        it.avatarPic
    }
    var sexDialogSelect = true
    val userBackground: LiveData<Drawable> = Transformations.map(SweetFishApplication.loginUser) {
        it.background
    }
    val userDescribe: LiveData<String?> = Transformations.map(SweetFishApplication.loginUser) {
        it.describe
    }
    val userPosition: LiveData<UserPositionInfo?> =
        Transformations.map(SweetFishApplication.loginUser) {
            it.position
        }

    @WorkerThread
    fun setAvatar(drawable: Drawable) {
        AppDatabase.getDatabase().userDao()
            .updateAvatar(SweetFishApplication.loginUser.value!!.id, drawable)
        SweetFishApplication.forceRefreshUserInfo()
    }

    @WorkerThread
    fun setName(name: String) {
        AppDatabase.getDatabase().userDao()
            .updateName(SweetFishApplication.loginUser.value!!.id, name)
        SweetFishApplication.forceRefreshUserInfo()
    }

    @WorkerThread
    fun setSex(sex: Boolean?) {
        AppDatabase.getDatabase().userDao()
            .updateSex(SweetFishApplication.loginUser.value!!.id, sex)
        SweetFishApplication.forceRefreshUserInfo()
    }

    @WorkerThread
    fun setPosition(province: String, city: String, district: String) {
        AppDatabase.getDatabase().userDao().updatePosition(
            SweetFishApplication.loginUser.value!!.id, UserPositionInfo(province, city, district)
        )
        SweetFishApplication.forceRefreshUserInfo()
    }

    @WorkerThread
    fun setDescribe(describe: String) {
        AppDatabase.getDatabase().userDao().updateDescribe(
            SweetFishApplication.loginUser.value!!.id, describe
        )
        SweetFishApplication.forceRefreshUserInfo()
    }

    @WorkerThread
    fun setBackground(drawable: Drawable) {
        AppDatabase.getDatabase().userDao()
            .updateBackground(SweetFishApplication.loginUser.value!!.id, drawable)
        SweetFishApplication.forceRefreshUserInfo()
    }

    class UserInfoEditPageActivityViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserInfoEditPageActivityViewModel() as T
        }
    }
}
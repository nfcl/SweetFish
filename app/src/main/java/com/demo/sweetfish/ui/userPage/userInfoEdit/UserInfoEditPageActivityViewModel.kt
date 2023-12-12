package com.demo.sweetfish.ui.userPage.userInfoEdit

import android.graphics.drawable.Drawable
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.demo.sweetfish.SweetFishApplication
import com.demo.sweetfish.logic.model.ImageSource
import com.demo.sweetfish.logic.model.User
import com.demo.sweetfish.logic.model.UserLocation
import com.demo.sweetfish.logic.repository.ImageSourceRepository
import com.demo.sweetfish.logic.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserInfoEditPageActivityViewModel : ViewModel() {

    private val editUserInfoId: MutableLiveData<Long> =
        MutableLiveData(SweetFishApplication.loginUserId.value!!)

    private val editUserInfo: LiveData<User?> = Transformations.switchMap(editUserInfoId) {
        UserRepository.findUserById(it)
    }

    val userId: LiveData<Long> = Transformations.map(editUserInfo) {
        it?.id
    }
    val userName: LiveData<String?> = Transformations.map(editUserInfo) {
        it?.name
    }
    val userSex: LiveData<Boolean?> = Transformations.map(editUserInfo) {
        it?.sex
    }
    val userAvatar: LiveData<ImageSource?> = Transformations.switchMap(editUserInfo) {
        if (it?.avatar != null) {
            ImageSourceRepository.findById(it.avatar)
        } else {
            null
        }
    }
    val userBackground: LiveData<ImageSource?> = Transformations.switchMap(editUserInfo) {
        if (it?.background != null) {
            ImageSourceRepository.findById(it.background)
        } else {
            null
        }
    }
    val userDescribe: LiveData<String?> = Transformations.map(editUserInfo) {
        it?.describe
    }
    val userPosition: LiveData<UserLocation?> = Transformations.map(editUserInfo) {
        it?.location
    }
    var sexDialogSelect = true

    fun setAvatar(drawable: Drawable) {
        CoroutineScope(Dispatchers.IO).launch {
            val imageId = ImageSourceRepository.insert(ImageSource(drawable))
            val temp = editUserInfo.value!!
            temp.avatar = imageId
            UserRepository.updateUserInfo(temp)
            refresh()
        }
    }

    fun setName(name: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val temp = editUserInfo.value!!
            temp.name = name
            UserRepository.updateUserInfo(temp)
            refresh()
        }
    }

    fun setSex(sex: Boolean?) {
        CoroutineScope(Dispatchers.IO).launch {
            val temp = editUserInfo.value!!
            temp.sex = sex
            UserRepository.updateUserInfo(temp)
            refresh()
        }
    }

    fun setPosition(province: String, city: String, district: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val temp = editUserInfo.value!!
            temp.location = UserLocation(province, city, district)
            UserRepository.updateUserInfo(temp)
            refresh()
        }
    }

    fun setDescribe(describe: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val temp = editUserInfo.value!!
            val result = describe.trimIndent()
            temp.describe = result.ifEmpty {
                null
            }
            UserRepository.updateUserInfo(temp)
            refresh()
        }
    }

    fun setBackground(drawable: Drawable) {
        CoroutineScope(Dispatchers.IO).launch {
            val imageId = ImageSourceRepository.insert(ImageSource(drawable))
            val temp = editUserInfo.value!!
            temp.background = imageId
            UserRepository.updateUserInfo(temp)
            refresh()
        }
    }

    private fun refresh() {
        if (Looper.myLooper() != null && Looper.myLooper() == Looper.getMainLooper()) {
            editUserInfoId.value = editUserInfoId.value
        } else {
            editUserInfoId.postValue(editUserInfoId.value)
        }
    }

    class UserInfoEditPageActivityViewModelFactory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserInfoEditPageActivityViewModel() as T
        }
    }
}
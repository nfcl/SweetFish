package com.demo.sweetfish

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.demo.sweetfish.logic.model.User
import com.demo.sweetfish.logic.repository.UserRepository

/**
 *  Application单例
 *
 *  用于获取程序运行时的context等
 */
class SweetFishApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        private val mLoginUserId: MutableLiveData<Long> = MutableLiveData()

        val loginUserId: LiveData<Long>
            get() = mLoginUserId

        val loginUser: LiveData<User> =
            Transformations.switchMap(mLoginUserId) { userId ->
                UserRepository.findUserById(userId)
            }

        fun setLoginUserId(id: Long) {
            mLoginUserId.postValue(id)
        }

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}
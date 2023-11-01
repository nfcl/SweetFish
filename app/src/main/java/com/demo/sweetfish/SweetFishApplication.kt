package com.demo.sweetfish

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.demo.sweetfish.logic.model.User

/**
 *  Application单例
 *
 *  用于获取程序运行时的context等
 */
class SweetFishApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        var loginUser: User? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}
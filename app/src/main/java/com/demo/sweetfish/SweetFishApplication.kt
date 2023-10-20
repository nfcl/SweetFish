package com.demo.sweetfish

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 *  Application单例
 *
 *  用于获取程序运行时的context等
 */
class SweetFishApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}
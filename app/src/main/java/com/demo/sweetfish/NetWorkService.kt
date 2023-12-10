package com.demo.sweetfish

import com.demo.sweetfish.logic.network.ImageSourceService
import com.demo.sweetfish.logic.network.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetWorkService {

    private const val BASE_URL = "http://10.0.2.2:8085/SweetFish_war_exploded/"

    private val retrofit =
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    fun userService(): UserService = create(UserService::class.java)

    fun imageSourceService(): ImageSourceService = create(ImageSourceService::class.java)

}
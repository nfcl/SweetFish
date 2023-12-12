package com.demo.sweetfish

import com.demo.sweetfish.logic.network.GoodsService
import com.demo.sweetfish.logic.network.GoodsWithSellerInfoService
import com.demo.sweetfish.logic.network.ImageSourceService
import com.demo.sweetfish.logic.network.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object NetWorkService {

    private const val BASE_URL = "http://10.0.2.2:8085/SweetFish_war_exploded/"

    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())//获取返回的字符串
        .addConverterFactory(GsonConverterFactory.create())//默认Gson转化库
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    fun userService(): UserService = create(UserService::class.java)

    fun imageSourceService(): ImageSourceService = create(ImageSourceService::class.java)

    fun goodsWithSellerInfoService(): GoodsWithSellerInfoService =
        create(GoodsWithSellerInfoService::class.java)

    fun goodsService(): GoodsService = create(GoodsService::class.java)

}
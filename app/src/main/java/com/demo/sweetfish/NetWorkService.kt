package com.demo.sweetfish

import com.demo.sweetfish.logic.network.ChatInfoService
import com.demo.sweetfish.logic.network.ChatService
import com.demo.sweetfish.logic.network.GoodsBuyInfoService
import com.demo.sweetfish.logic.network.GoodsPreviewImageService
import com.demo.sweetfish.logic.network.GoodsService
import com.demo.sweetfish.logic.network.GoodsWithSellerInfoService
import com.demo.sweetfish.logic.network.ImageSourceService
import com.demo.sweetfish.logic.network.MessageInfoService
import com.demo.sweetfish.logic.network.MessageService
import com.demo.sweetfish.logic.network.SearchResultPageUserInfoService
import com.demo.sweetfish.logic.network.UserFollowService
import com.demo.sweetfish.logic.network.UserService
import com.demo.sweetfish.logic.network.UserWithFollowInfoService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object NetWorkService {

    private const val BASE_URL = "http://192.168.137.1:8085/SweetFish_war_exploded/"

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

    fun goodsPreviewImageService(): GoodsPreviewImageService =
        create(GoodsPreviewImageService::class.java)

    fun userFollowService(): UserFollowService = create(UserFollowService::class.java)

    fun searchResultPageUserInfoService(): SearchResultPageUserInfoService =
        create(SearchResultPageUserInfoService::class.java)

    fun userWithFollowInfoService(): UserWithFollowInfoService =
        create(UserWithFollowInfoService::class.java)

    fun goodsBuyInfoService(): GoodsBuyInfoService = create(GoodsBuyInfoService::class.java)

    fun chatService(): ChatService = create(ChatService::class.java)

    fun chatInfoService(): ChatInfoService = create(ChatInfoService::class.java)

    fun messageService(): MessageService = create(MessageService::class.java)

    fun messageInfoService(): MessageInfoService = create(MessageInfoService::class.java)

}
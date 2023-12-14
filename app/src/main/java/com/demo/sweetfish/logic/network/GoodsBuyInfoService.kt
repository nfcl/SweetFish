package com.demo.sweetfish.logic.network

import com.demo.sweetfish.logic.model.GoodsBuyInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GoodsBuyInfoService {

    @GET("ServletGoodsBuyInfoFindPublishedByBuyerId")
    fun findPublishedByBuyerId(
        @Query("sellerId") sellerId: Long,
    ): Call<List<GoodsBuyInfo>>

    @GET("ServletGoodsBuyInfoFindBoughtByBuyerId")
    fun findBoughtByBuyerId(
        @Query("buyerId") buyerId: Long,
    ): Call<List<GoodsBuyInfo>>

    @GET("ServletGoodsBuyInfoFindBoughtByGoodsState")
    fun findBoughtByGoodsState(
        @Query("state") state: Int,
        @Query("buyerId") buyerId: Long,
    ): Call<List<GoodsBuyInfo>>

    @GET("ServletGoodsBuyInfoFindSoldBySellerId")
    fun findSoldBySellerId(
        @Query("sellerId") sellerId: Long,
    ): Call<List<GoodsBuyInfo>>

    @GET("ServletGoodsBuyInfoFindSoldByGoodsState")
    fun findSoldByGoodsState(
        @Query("state") state: Int,
        @Query("sellerId") sellerId: Long,
    ): Call<List<GoodsBuyInfo>>

}
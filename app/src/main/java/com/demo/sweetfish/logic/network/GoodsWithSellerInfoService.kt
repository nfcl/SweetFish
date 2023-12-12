package com.demo.sweetfish.logic.network

import com.demo.sweetfish.logic.model.GoodsWithSellerInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GoodsWithSellerInfoService {

    @GET("ServletGoodsWithSellerInfoFindAll")
    fun findAll(): Call<List<GoodsWithSellerInfo>>

    @GET("ServletGoodsWithSellerInfoFindLikeGoodsTitle")
    fun findLikeGoodsTitle(@Query("goodsTitle") goodsTitle: String): Call<List<GoodsWithSellerInfo>>

}
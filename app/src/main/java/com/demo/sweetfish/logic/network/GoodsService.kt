package com.demo.sweetfish.logic.network

import com.demo.sweetfish.logic.model.Goods
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GoodsService {

    @POST("ServletGoodsInsert")
    fun insert(@Body goods: Goods): Call<Long>

    @POST("ServletGoodsUpdate")
    fun update(@Body goods: Goods): Call<ResponseBody?>

    @GET("ServletGoodsFindAll")
    fun findAll(): Call<List<Goods>>

    @GET("ServletGoodsFindByGoodsId")
    fun findByGoodsId(@Query("goodsId") goodsId: Long): Call<Goods?>

    @GET("ServletGoodsFindBySellerId")
    fun findBySellerId(@Query("sellerId") sellerId: Long): Call<List<Goods>>

    @GET("ServletGoodsFindLikeGoodsTitle")
    fun findLikeGoodsTitle(@Query("goodsTitle") goodsTitle: String): Call<List<Goods>>

    @POST("ServletGoodsDelete")
    fun delete(@Body goods: Goods): Call<ResponseBody?>

}
package com.demo.sweetfish.logic.network

import com.demo.sweetfish.logic.model.GoodsPreviewImage
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface GoodsPreviewImageService {

    @POST("ServletGoodsPreviewImageInsert")
    fun insert(@Body goodsPreviewImage: List<GoodsPreviewImage>): Call<ResponseBody?>

    @GET("ServletGoodsPreviewImageFindContentByGoodsId")
    fun findContentByGoodsId(@Query("goodsId") goodsId: Long): Call<List<Long>>

    @POST("ServletGoodsPreviewImageDelete")
    fun delete(@Body goodsPreviewImage: GoodsPreviewImage): Call<ResponseBody?>

}
package com.demo.sweetfish.logic.network

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Streaming


interface ImageSourceService {

    @Multipart
    @POST("ServletImageSourceInsert")
    fun insert(
        @Part file: MultipartBody.Part,
    ): Call<Long>

    @Streaming
    @GET("ServletImageSourceFindById")
    fun findById(@Query("id") id: Long): Call<ResponseBody?>

}
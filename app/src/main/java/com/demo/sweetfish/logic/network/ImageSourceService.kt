package com.demo.sweetfish.logic.network

import com.demo.sweetfish.logic.model.ImageSource
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ImageSourceService {

    @POST("ServletImageSourceInsert")
    fun insert(@Body imageSource: ImageSource): Call<Long>

    @GET("ServletImageSourceInsert")
    fun findById(@Query("id") id: Long): Call<ImageSource?>

}
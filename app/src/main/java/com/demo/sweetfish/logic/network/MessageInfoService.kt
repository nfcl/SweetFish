package com.demo.sweetfish.logic.network

import com.demo.sweetfish.logic.model.MessageInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MessageInfoService {

    @GET("ServletMessageInfoFindByUserId")
    fun findByUserId(@Query("userId") userId: Long): Call<List<MessageInfo>>

}
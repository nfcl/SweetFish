package com.demo.sweetfish.logic.network

import com.demo.sweetfish.logic.model.Message
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MessageService {
    @POST("ServletMessageInsert")
    fun insert(@Body message: Message): Call<Long>

    @POST("ServletMessageUpdate")
    fun update(@Body message: Message): Call<ResponseBody?>

    @GET("ServletMessageFindAll")
    fun findAll(): Call<List<Message>>

    @GET("ServletMessageFindByChatId")
    fun findByChatId(@Query("chatId") chatId: Long): Call<List<Message>>

    @GET("ServletMessageFindBySenderId")
    fun findBySenderId(@Query("senderId") senderId: Long): Call<List<Message>>

    @POST("ServletMessageDelete")
    fun delete(@Body message: Message): Call<ResponseBody?>

}
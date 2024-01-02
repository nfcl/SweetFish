package com.demo.sweetfish.logic.network

import com.demo.sweetfish.logic.model.Chat
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ChatService {


    @POST("ServletChatInsert")
    fun insert(@Body chat: Chat): Call<Long>

    @POST("ServletChatUpdate")
    fun update(@Body chat: Chat): Call<ResponseBody?>

    @GET("ServletChatFindAll")
    fun findAll(): Call<List<Chat>>

    @GET("ServletChatFindByBuyerId")
    fun findByUserId(@Query("userId") userId: Long): Call<List<Chat>>

    @GET("ServletChatFindByGoodsId")
    fun findByGoodsId(@Query("goodsId") goodsId: Long): Call<List<Chat>>

    @POST("ServletChatDelete")
    fun delete(@Body chat: Chat): Call<ResponseBody?>

    @POST("ServletChatIsExist")
    fun isExisted(@Body chat: Chat): Call<Long>

}
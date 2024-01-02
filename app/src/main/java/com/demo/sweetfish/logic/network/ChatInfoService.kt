package com.demo.sweetfish.logic.network

import com.demo.sweetfish.logic.model.ChatInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ChatInfoService {

    @GET("ServletChatFindByChatId")
    fun findByChatId(@Query("chatId") chatId: Long): Call<ChatInfo?>

}
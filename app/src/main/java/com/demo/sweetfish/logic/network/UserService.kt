package com.demo.sweetfish.logic.network

import com.demo.sweetfish.logic.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {

    @POST("ServletUserInsert")
    fun insert(
        @Body user: User,
    ): Call<Long>

    @POST("ServletUserUpdate")
    fun update(
        @Body user: User,
    )

    @GET("ServletUserFindById")
    fun findById(
        @Query("id") id: Long,
    ): Call<User>

    @GET("ServletUserFindById")
    fun findByAccountAndPassword(
        @Query("account") account: String,
        @Query("password") password: String,
    ): Call<User>

    @GET("ServletUserFindLikeName")
    fun findLikeName(
        @Query("name") name: String,
    ): Call<List<User>>

    @POST("ServletUserDeleteAll")
    fun deleteAll()

}
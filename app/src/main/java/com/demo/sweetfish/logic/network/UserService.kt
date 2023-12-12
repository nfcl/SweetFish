package com.demo.sweetfish.logic.network

import com.demo.sweetfish.logic.model.User
import okhttp3.ResponseBody
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
    ): Call<ResponseBody>

    @GET("ServletUserFindById")
    fun findById(
        @Query("id") id: Long,
    ): Call<User?>

    @GET("ServletUserCheckDuplicateAccount")
    fun checkDuplicateAccount(
        @Query("account") account: String,
    ): Call<Boolean>

    @GET("ServletUserFindByAccountAndPassword")
    fun findByAccountAndPassword(
        @Query("account") account: String,
        @Query("password") password: String,
    ): Call<User?>

    @GET("ServletUserFindLikeName")
    fun findLikeName(
        @Query("name") name: String,
    ): Call<List<User>>

    @POST("ServletUserDeleteAll")
    fun deleteAll()

}
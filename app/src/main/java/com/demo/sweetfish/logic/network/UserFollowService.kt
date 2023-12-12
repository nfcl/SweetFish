package com.demo.sweetfish.logic.network

import com.demo.sweetfish.logic.model.UserFollow
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserFollowService {

    @POST("ServletUserFollowInsert")
    fun insert(@Body userFollow: UserFollow): Call<ResponseBody?>

    @POST("ServletUserFollowDelete")
    fun delete(@Body userFollow: UserFollow): Call<ResponseBody?>

    @GET("ServletUserFollowFindByFanId")
    fun findByFanId(@Query("fanId") fanId: Long): Call<List<Long>>

    @GET("ServletUserFollowFindByFollowId")
    fun findByFollowId(@Query("followId") followId: Long): Call<List<Long>>

    @GET("ServletUserFollowGetFanNum")
    fun getFanNum(@Query("followId") followId: Long): Call<Long>

    @GET("ServletUserFollowGetFollowedNum")
    fun getFollowNum(@Query("fanId") fanId: Long): Call<Long>

    @POST("ServletUserFollowIsFollowed")
    fun isFollowed(@Body userFollow: UserFollow): Call<Boolean>

}
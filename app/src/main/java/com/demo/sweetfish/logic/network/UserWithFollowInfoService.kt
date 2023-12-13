package com.demo.sweetfish.logic.network

import com.demo.sweetfish.logic.model.UserWithFollowInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserWithFollowInfoService {

    @GET("ServletUserWithFollowInfoGetFollowUser")
    fun getFollowUser(
        @Query("fanId") fanId: Long,
        @Query("loginId") loginId: Long,
    ): Call<List<UserWithFollowInfo>>

    @GET("ServletUserWithFollowInfoGetFanUser")
    fun getFanUser(
        @Query("followId") followId: Long,
        @Query("loginId") loginId: Long,
    ): Call<List<UserWithFollowInfo>>

}
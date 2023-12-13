package com.demo.sweetfish.logic.network

import com.demo.sweetfish.logic.model.SearchResultPageUserInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchResultPageUserInfoService {

    @GET("ServletSearchResultPageUserInfoFindLikeUserName")
    fun findLikeUserName(@Query("userName") userName: String): Call<List<SearchResultPageUserInfo>>

}
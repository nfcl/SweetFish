package com.demo.sweetfish.logic.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.demo.sweetfish.logic.model.SearchResultPageUserInfo

@Dao
interface SearchResultPageUserInfoDao {

    @Query("""
        select 
            User.id as userId,
            User.name as userName,
            User.avatarPic as userAvatar,
            (select count(*) from UserFollow where UserFollow.followId=User.id) as userFanNum
        from
            User
        where
            User.name like '%'||:searchContent||'%'
    """)
    fun findLikeUserName(searchContent: String): List<SearchResultPageUserInfo>

    @Query("""
        select 
            User.id as userId,
            User.name as userName,
            User.avatarPic as userAvatar,
            (select count(*) from UserFollow where UserFollow.followId=User.id) as userFanNum
        from
            User
        where
            User.name like '%'||:searchContent||'%'
    """)
    fun findLikeUserNameReturnLiveData(searchContent: String): LiveData<List<SearchResultPageUserInfo>>

}
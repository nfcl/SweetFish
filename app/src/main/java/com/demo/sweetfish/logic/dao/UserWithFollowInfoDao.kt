package com.demo.sweetfish.logic.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.demo.sweetfish.logic.model.UserWithFollowInfo

@Dao
interface UserWithFollowInfoDao {

    @Query(
        """
        select 
            User.id as userId,
            User.name as userName,
            User.describe as userDescribe,
            User.avatar as userAvatar,
            (select count(*) from UserFollow where UserFollow.followId=User.id) as userFanNum,
            (select count(*) from UserFollow where UserFollow.followId=User.id and UserFollow.fanId=:loginId) as isFollowed 
        from 
            User 
        where User.id in (select UserFollow.followId from UserFollow where fanId=:fanId)"""
    )
    fun getFollowUser(fanId: Long, loginId: Long): List<UserWithFollowInfo>

    @Query(
        """
        select 
            User.id as userId,
            User.name as userName,
            User.describe as userDescribe,
            User.avatar as userAvatar,
            (select count(*) from UserFollow where UserFollow.followId=User.id) as userFanNum,
            (select count(*) from UserFollow where UserFollow.followId=User.id and UserFollow.fanId=:loginId) as isFollowed 
        from 
            User 
        where User.id in (select UserFollow.followId from UserFollow where fanId=:fanId)"""
    )
    fun getFollowUserReturnLivData(fanId: Long, loginId: Long): LiveData<List<UserWithFollowInfo>>

    @Query(
        """
        select 
            User.id as userId,
            User.name as userName,
            User.describe as userDescribe,
            User.avatar as userAvatar,
            (select count(*) from UserFollow where UserFollow.followId=User.id) as userFanNum,
            (select count(*) from UserFollow where UserFollow.followId=User.id and UserFollow.fanId=:loginId) as isFollowed 
        from 
            User 
        where User.id in (select UserFollow.fanId from UserFollow where followId=:followId)"""
    )
    fun getFanUser(followId: Long, loginId: Long): List<UserWithFollowInfo>

    @Query(
        """
        select 
            User.id as userId,
            User.name as userName,
            User.describe as userDescribe,
            User.avatar as userAvatar,
            (select count(*) from UserFollow where UserFollow.followId=User.id) as userFanNum,
            (select count(*) from UserFollow where UserFollow.followId=User.id and UserFollow.fanId=:loginId) as isFollowed 
        from 
            User 
        where User.id in (select UserFollow.fanId from UserFollow where followId=:followId)"""
    )
    fun getFanUserReturnLivData(followId: Long, loginId: Long): LiveData<List<UserWithFollowInfo>>

}
package com.demo.sweetfish.logic.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.demo.sweetfish.logic.model.UserFollow

/**
 * 关注表不需要Update
 * 直接删除和插入即可
 */
@Dao
interface UserFollowDao {

    @Insert
    fun insert(followData: UserFollow)

    @Delete
    fun delete(followData: UserFollow)

    @Query("select * from UserFollow")
    fun findAll(): List<UserFollow>

    /**
     * 防止混淆特别标注一下
     * 这是通过粉丝Id查找这个人关注的所有人的Id
     */
    @Query("select followId from UserFollow where fanId=:fanId")
    fun findByFanId(fanId: Long): List<Long>

    /**
     * 防止混淆特别标注一下
     * 这是通过被关注者的Id查找所有关注这个人的粉丝们的Id
     */
    @Query("select fanId from UserFollow where followId=:followId")
    fun findByFollowId(followId: Long): List<Long>

    @Query("select count(*) from UserFollow where fanId=:fanId")
    fun getFollowsNum(fanId: Long): Int

    @Query("select count(*) from UserFollow where fanId=:fanId")
    fun getFollowsNumReturnLiveData(fanId: Long): LiveData<Int>

    @Query("select count(*) from UserFollow where followId=:followId")
    fun getFansNum(followId: Long): Int

    @Query("select count(*) from UserFollow where followId=:followId")
    fun getFansNumReturnLiveData(followId: Long): LiveData<Int>

    @Query("select count(*) from UserFollow where followId=:followId and fanId=:fanId")
    fun isFollowed(followId: Long, fanId: Long): LiveData<Int>

}
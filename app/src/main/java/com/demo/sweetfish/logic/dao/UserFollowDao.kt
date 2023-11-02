package com.demo.sweetfish.logic.dao

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

}
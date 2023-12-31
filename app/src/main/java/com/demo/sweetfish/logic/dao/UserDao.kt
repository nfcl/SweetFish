package com.demo.sweetfish.logic.dao

import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.demo.sweetfish.logic.model.User
import com.demo.sweetfish.logic.model.UserLocation

@Dao
interface UserDao {

    /**
     * 插入一条新的用户信息
     * @param user 新用户信息
     * @return 新用户的Id
     */
    @Insert
    fun insert(user: User): Long

    @Update
    fun update(user: User)

    @Query("update user set name=:name where id=:id")
    fun updateName(id: Long, name: String?)

    @Query("update user set sex=:sex where id=:id")
    fun updateSex(id: Long, sex: Boolean?)

    @Query("update user set avatar=:avatar where id=:id")
    fun updateAvatar(id: Long, avatar: Drawable)

    @Query("update user set location=:position where id=:id")
    fun updatePosition(id: Long, position: UserLocation)

    @Query("update user set describe=:describe where id=:id")
    fun updateDescribe(id: Long, describe: String?)

    @Query("update user set background=:background where id=:id")
    fun updateBackground(id: Long, background: Drawable)

    /**
     * 通过Id查找用户信息
     * @return 找到的用户信息,如果没找到则返回null
     */
    @Query("select * from User where id=:id")
    fun findById(id: Long): User?

    @Query("select * from User where id=:id")
    fun findByIdReturnLivData(id: Long): LiveData<User?>

    /**
     * 通过账号和密码查询用户Id(登录)
     * @return 找到的用户信息,如果没找到则返回null
     */
    @Query("select id from User where account=:account and password=:password")
    fun findByAccountAndPassword(account: String, password: String): Long?

    /**
     * 通过name查找拥有相似名称的用户信息
     * @return 返回一个包含拥有相似名称的用户信息列表
     */
    @Query("select * from user where name like '%'||:name||'%'")
    fun findLikeName(name: String): List<User>

    @Query("select * from user where name like '%'||:name||'%'")
    fun findLikeNameReturnLiveData(name: String): LiveData<List<User>>

    /**
     * 删除所有用户
     */
    @Query("delete from User")
    fun deleteAll()

}
package com.demo.sweetfish.logic.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.demo.sweetfish.logic.model.User

@Dao
interface UserDao {

    /**
     * 插入一条新的用户信息
     * @param user 新用户信息
     * @return 新用户的Id
     */
    @Insert
    fun insert(user: User): Long

    /**
     * 插入列表内的所有用户信息
     * @param userList 新用户信息列表
     */
    @Insert
    fun insert(userList: List<User>)

    /**
     * 更改用户的信息
     * @param newUser 更新后的用户信息
     */
    @Update
    fun update(newUser: User)

    /**
     * 查找所有的用户信息
     * @return 包含所有用户信息的列表
     */
    @Query("select * from User")
    fun findAll(): List<User>

    /**
     * 通过Id查找用户信息
     * @return 找到的用户信息,如果没找到则返回null
     */
    @Query("select * from User where id=:id")
    fun findById(id: Long): LiveData<User?>

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
    @Query("select * from user where name like :name")
    fun findLikeName(name: String): List<User>

    /**
     * 删除用户
     * @param user 要删除的用户信息
     */
    @Delete
    fun delete(user: User)

    /**
     * 删除所有用户
     */
    @Query("delete from User")
    fun deleteAll()

}
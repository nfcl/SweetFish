package com.demo.poshmarkinc.logic.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.demo.poshmarkinc.logic.model.User

@Dao
interface UserDao {

    /**
     * 插入一条新的User信息
     * @param user 新的用户信息
     * @return 新用户的Id
     */
    @Insert
    fun insert(user: User): Long

    /**
     * 更改用户的信息
     * @param newUser 更新后的用户信息
     */
    @Update
    fun update(newUser: User)

    /**
     * 查找所有的用户信息
     * @return 所有用户信息的列表
     */
    @Query("select * from User")
    fun findAll(): List<User>

    /**
     * 通过Id查找用户信息
     * @return 找到的用户信息,如果没找到则返回null
     */
    @Query("select * from User where id = :id")
    fun findById(id: Long): User?

    /**
     * 通过name查找拥有相似名称的用户信息
     * @return 返回一个包含拥有相似名称的用户信息列表
     */
    @Query("select * from user where name like :name")
    fun findByName(name: String): List<User>

    /**
     * 删除用户
     * @param user 要删除的用户信息
     */
    @Delete
    fun delete(user: User)

}
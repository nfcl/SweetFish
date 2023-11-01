package com.demo.sweetfish.logic.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface RegisterDao {

    @Query("select count(*) from user where account=:account")
    fun checkMultipleAccount(account: String): Int

}
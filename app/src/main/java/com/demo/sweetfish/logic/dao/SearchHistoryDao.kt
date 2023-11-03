package com.demo.sweetfish.logic.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface SearchHistoryDao {

    @Query("insert or replace into SearchHistory(content,recordTime)values(:content,strftime('%s','now'))")
    fun insertOrUpdate(content: String)

    @Query("delete from SearchHistory where content=:content")
    fun delete(content: String)

    @Query("select content from SearchHistory order by recordTime desc")
    fun findAll(): List<String>

}
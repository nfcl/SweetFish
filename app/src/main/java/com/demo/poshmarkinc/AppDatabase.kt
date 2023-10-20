package com.demo.poshmarkinc

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.demo.poshmarkinc.logic.dao.GoodsDao
import com.demo.poshmarkinc.logic.dao.UserDao

/**
 * 使用单例模式的数据库类
 *
 * version 用于指定数据库的版本
 *
 * entities 用于指定数据库包含了哪些Dao实体类(表)
 */
@Database(version = 0, entities = [UserDao::class, GoodsDao::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        private var instance: AppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AppDatabase {
            //如果已经存在instance则直接返回即可
            instance?.let {
                return it
            }
            //否则重新连接到数据库生成instance并返回
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).build().apply {
                instance = this
            }
        }
    }
}
package com.demo.sweetfish

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.demo.sweetfish.logic.dao.ChatInfoDao
import com.demo.sweetfish.logic.dao.GoodsDao
import com.demo.sweetfish.logic.dao.GoodsWithSellerInfoDao
import com.demo.sweetfish.logic.dao.MessageDao
import com.demo.sweetfish.logic.dao.UserDao
import com.demo.sweetfish.logic.model.ChatInfo
import com.demo.sweetfish.logic.model.Goods
import com.demo.sweetfish.logic.model.Message
import com.demo.sweetfish.logic.model.User

/**
 * 使用单例模式的数据库类
 *
 * version 用于指定数据库的版本
 *
 * entities 用于指定数据库包含了哪些Dao实体类(表)
 */
@Database(version = 1, entities = [User::class, Goods::class, Message::class, ChatInfo::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun goodsDao(): GoodsDao

    abstract fun goodsWithSellerInfoDao(): GoodsWithSellerInfoDao

    abstract fun messageDao(): MessageDao

    abstract fun chatInfoDao(): ChatInfoDao

    companion object {

        private var instance: AppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context = SweetFishApplication.context): AppDatabase {
            //如果已经存在instance则直接返回即可
            instance?.let {
                return it
            }
            //否则重新连接到数据库生成instance并返回
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "app_database"
            ).build().apply {
                instance = this
            }
        }
    }
}
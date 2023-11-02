package com.demo.sweetfish

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.demo.sweetfish.logic.dao.GoodsDao
import com.demo.sweetfish.logic.dao.GoodsWithSellerInfoDao
import com.demo.sweetfish.logic.dao.RegisterDao
import com.demo.sweetfish.logic.dao.UserDao
import com.demo.sweetfish.logic.dao.UserFollowDao
import com.demo.sweetfish.logic.model.Goods
import com.demo.sweetfish.logic.model.User
import com.demo.sweetfish.logic.model.UserFollow

/**
 * 使用单例模式的数据库类
 *
 * version 用于指定数据库的版本
 *
 * entities 用于指定数据库包含了哪些实体类(表)
 */
@Database(
    version = 1,
    entities = [User::class, Goods::class, UserFollow::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun goodsDao(): GoodsDao

    abstract fun goodsWithSellerInfoDao(): GoodsWithSellerInfoDao

    abstract fun registerDao(): RegisterDao

    abstract fun UserFollowDao(): UserFollowDao

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
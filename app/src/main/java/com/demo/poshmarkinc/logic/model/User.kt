package com.demo.poshmarkinc.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 用户类
 *
 * 用于保存用户信息
 * @property id     自动生成的唯一标识符
 * @property name   昵称
 * @property sex    性别
 */
@Entity
data class User(
    var name: String,
    var sex: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
package com.demo.sweetfish.logic.model

import android.graphics.drawable.Drawable
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 用户类
 *
 * 用于保存用户信息
 * @property id         唯一标识符
 * @property name       昵称
 * @property sex        性别 true男 false女
 * @property account    账号
 * @property password   密码
 * @property avatarPic  头像
 * @property position   位置
 * @property describe   个人简介
 */
@Entity
data class User(
    var account: String,
    var password: String,
    var avatarPic: Drawable,
    var background: Drawable,
    var name: String? = null,
    var sex: Boolean? = null,
    var position: UserLocation? = null,
    var describe: String? = null,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
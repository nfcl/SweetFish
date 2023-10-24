package com.demo.sweetfish.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 用户类
 *
 * 用于保存用户信息
 * @property id         唯一标识符
 * @property name       昵称
 * @property sex        性别
 * @property account    账号
 * @property password   密码
 * @property avatarPic 头像的二进制数据
 */
@Entity
data class User(
    var name: String,
    var sex: Boolean,
    var account: String,
    var password: String,
    var avatarPic: ByteArray
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (name != other.name) return false
        if (sex != other.sex) return false
        if (account != other.account) return false
        if (password != other.password) return false
        if (!avatarPic.contentEquals(other.avatarPic)) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + sex.hashCode()
        result = 31 * result + account.hashCode()
        result = 31 * result + password.hashCode()
        result = 31 * result + avatarPic.contentHashCode()
        result = 31 * result + id.hashCode()
        return result
    }
}
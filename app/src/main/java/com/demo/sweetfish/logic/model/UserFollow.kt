package com.demo.sweetfish.logic.model

import androidx.room.Entity

/**
 * 用户的关注关系
 * @param followId 被关注者的Id
 * @param fanId 粉丝的Id
 */
@Entity(primaryKeys = ["followId", "fanId"])
data class UserFollow(
    val followId: Long,
    val fanId: Long,
)
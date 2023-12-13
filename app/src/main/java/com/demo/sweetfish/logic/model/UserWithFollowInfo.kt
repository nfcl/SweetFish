package com.demo.sweetfish.logic.model

data class UserWithFollowInfo(
    val userId: Long,
    val userName: String?,
    val userDescribe: String?,
    val userAvatar: Long,
    val userFanNum: Int,
    val isFollowed: Int,
)
package com.demo.sweetfish.logic.model

import android.graphics.drawable.Drawable

data class UserWithFollowInfo(
    val userId: Long,
    val userName: String?,
    val userDescribe: String?,
    val userAvatar: Drawable,
    val userFanNum: Int,
    val isFollowed: Int,
)
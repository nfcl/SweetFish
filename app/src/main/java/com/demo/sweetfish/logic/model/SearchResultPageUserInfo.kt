package com.demo.sweetfish.logic.model

import android.graphics.drawable.Drawable

data class SearchResultPageUserInfo(
    val userId: Long,
    val userName: String?,
    val userAvatar: Drawable,
    val userFanNum: Int,
)
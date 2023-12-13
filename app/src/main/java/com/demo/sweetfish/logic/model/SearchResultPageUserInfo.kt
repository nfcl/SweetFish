package com.demo.sweetfish.logic.model

data class SearchResultPageUserInfo(
    val userId: Long,
    val userName: String?,
    val userAvatar: Long,
    val userFanNum: Int,
)
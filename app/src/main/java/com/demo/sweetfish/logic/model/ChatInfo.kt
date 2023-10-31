package com.demo.sweetfish.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey
/**
 * @property goodsId 商品Id
 * @property buyerId 买家Id
 */
@Entity
data class ChatInfo(
    val goodsId: Long,
    val buyerId: Long,
) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
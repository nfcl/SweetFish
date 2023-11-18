package com.demo.sweetfish.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * @property goodsId 商品Id
 * @property buyerId 购买者Id
 * @property buyTime 购买时间
 * @property state 订单状态
 * @property position 目的地
 */
@Entity
data class GoodsBuy(
    val goodsId: Long,
    val buyerId: Long,
    val buyTime: Date,
    val state: String,
    val position: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
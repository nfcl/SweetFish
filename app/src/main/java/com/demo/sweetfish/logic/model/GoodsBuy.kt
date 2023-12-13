package com.demo.sweetfish.logic.model

import java.util.Date

/**
 * @property goodsId 商品Id
 * @property buyerId 购买者Id
 * @property buyTime 购买时间
 * @property state 订单状态
 * @property position 目的地
 */
data class GoodsBuy(
    val goodsId: Long,
    val buyerId: Long,
    val buyTime: Date,
    val state: GoodsState,
    val position: String,
    var id: Long = 0,
)
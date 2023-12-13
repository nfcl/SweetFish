package com.demo.sweetfish.logic.model

/**
 * @property goodsId 商品Id
 * @property buyerId 买家Id
 * @property buyerReadMessageId 买家已读的消息Id
 * @property sellerReadMessageId 卖家已读的消息Id
 */
data class ChatInfo(
    val goodsId: Long,
    val buyerId: Long,
    val buyerReadMessageId: Long,
    val sellerReadMessageId: Long,
    var id: Long = 0,
)
package com.demo.sweetfish.logic.model

data class Goods(
    var title: String,
    var price: Double,
    var goodsinfo: String,
    var location: String,
    var sellerId: Long,
    var id: Long = 0,
)
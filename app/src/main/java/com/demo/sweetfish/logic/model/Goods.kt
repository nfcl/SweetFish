package com.demo.sweetfish.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Goods(
    var title: String,
    var price: Double,
    var goodsinfo: String,
    var sellerId: Long,
) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
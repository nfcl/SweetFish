package com.demo.sweetfish.logic.model

enum class GoodsState(val parseNum: Int) {
    PendingPayment(0),      //待付款
    ToBeShipped(1),         //代发货
    ToBeReceived(2),        //待收货
    ToBeEvaluated(3),       //待评价
    RefundsAreOngoing(4);   //退款中

    companion object {
        fun fromParseNum(num: Int): GoodsState {
            return when (num) {
                0 -> PendingPayment
                1 -> ToBeShipped
                2 -> ToBeReceived
                3 -> ToBeEvaluated
                4 -> RefundsAreOngoing
                else -> throw Exception("错误的枚举值")
            }
        }
    }

}
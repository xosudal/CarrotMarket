package com.study.carrotmarket.constant

interface MainMarketContract {
    interface View {
        fun setUsedItemList(list : List<SimpleUsedItemResponse>)
        fun setDetailUsedItem(item : DetailUsedItemResponse)
    }

    interface Presenter {
        fun getSimpleUsedItem() : Boolean
        fun getDetailUsedItem(id : Int) : Boolean
    }
}
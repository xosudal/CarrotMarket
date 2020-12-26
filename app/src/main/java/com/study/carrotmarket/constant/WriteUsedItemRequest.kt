package com.study.carrotmarket.constant

import java.time.LocalDateTime

data class SimpleUsedItemResponse(
    val id : Int,
    val nickname : String,
    val region : String,
    val e_mail : String,
    val modifiedTime : String,
    val price : Int,
    val category: String,
    val images: Int,
    val title : String,
    val desc : String,
    val chatCnt : Int,
    val interestCnt : Int)


data class WriteUsedItemRequest(
    val nickname : String,
    val region : String,
    val e_mail : String,
    val price : Int,
    val category: String,
    val images: Int,
    val title : String,
    val desc : String,
    val priceDeal : Boolean)
package com.study.carrotmarket.constant

data class SimpleUsedItemResponse(
    val id : Int,
    val nickname : String,
    val region : String,
    val price : Int,
    val category: String,
    val images: Int,
    val title : String,
    val desc : String)


data class UsedItems(
    val nickname : String,
    val region : String,
    val price : Int,
    val category: String,
    val images: Int,
    val title : String,
    val desc : String)
package com.study.carrotmarket.constant

data class UserRequest(
    val nickname: String,
    val e_mail: String,
    val cellphone: String,
    val region: String,
    val regionLevel: Int,
    val profileName: String
)
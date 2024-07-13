package com.example.bankingapp.database

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val balance: Double
)

data class Transfer(
    val senderId: Int,
    val receiverId: Int,
    val amount: Double
)

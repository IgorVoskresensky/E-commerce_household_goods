package ru.ivos.e_commerce_household_goods.data

data class User (
    val name: String,
    val phone: String,
    val email: String,
    val password: String,
    val imageUrl: String = ""
        )
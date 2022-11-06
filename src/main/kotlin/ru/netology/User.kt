package ru.netology

data class User (
    var userId: Int = 0x0000,
    var friends: List<User> = emptyList()
)
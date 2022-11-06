package ru.netology

abstract class Entity (
    open var id: Int,
    open val date: Int,
    open var ownerId: Int,
    open var isDeleted: Boolean
)
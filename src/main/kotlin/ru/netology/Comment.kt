package ru.netology

data class Comment (
    var id: Int = 0x0000,
    var noteId: Int = 0x0000,
    var ownerId: Int = 0x0000,
    val date: Int = (System.currentTimeMillis()/1000).toInt(),
    val message: String = "enter your comment here",
    var replyToUser: Int = 0x0000,
    var guid: Long = 0x0000,
    var isDeleted: Boolean = false
)

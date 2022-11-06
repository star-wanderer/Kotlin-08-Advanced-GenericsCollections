package ru.netology

data class Note (
    override var id: Int = 0x0000,
    override val date: Int = (System.currentTimeMillis()/1000).toInt(),
    override var ownerId: Int = 0x0000,
    override var isDeleted: Boolean = false,
    val title : String = "",
    val text: String = "",
    val comments: Int = 0,
    val readComments: Int = 0,
    val viewUrl: String = "",
    val privacyView: String = "all",
    val privacyComment: String = "all",
    val canComment: Int = 0,
    val textWiki: String = ""
) : Entity(id, date, ownerId, isDeleted)
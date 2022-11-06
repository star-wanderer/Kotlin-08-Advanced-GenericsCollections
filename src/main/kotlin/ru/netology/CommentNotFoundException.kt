package ru.netology

class CommentNotFoundException (id: Int) : RuntimeException("Comment with $id was not found")


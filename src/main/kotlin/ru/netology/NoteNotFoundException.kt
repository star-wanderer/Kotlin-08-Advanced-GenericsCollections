package ru.netology
class NoteNotFoundException (id: Int) : RuntimeException("Note with $id was not found")
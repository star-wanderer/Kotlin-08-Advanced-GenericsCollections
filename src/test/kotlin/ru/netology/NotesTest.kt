package ru.netology

import org.junit.Test

import org.junit.Assert.*

class NotesTest {

    @Test
    fun addNotes() {

        // Arrange -> prepare variables
        val testId = 0x0002
        var myNotes = Entities<Note>()

        // Act -> add notes
        myNotes.add(Note(title = "My First Note",text = "Today is Sunday 6th of November when I created this note"))
        myNotes.add(Note(title = "Cold November",text = "Winter is nearby...."))

        // Assert -> id of the last note is correct
        assertEquals(0x0002,myNotes.getById(testId).id)
    }

    @Test
    fun getNotesByUser() {

        // Arrange -> prepare variables
        val user1 = User(0x0001)
        val user2 = User(0x0002)
        var myNotes = Entities<Note>()

        // Act -> add notes
        myNotes.add(Note(ownerId = user1.userId,title = "My First Note",text = "I'm user1. This is my first note"))
        myNotes.add(Note(ownerId = user2.userId,title = "Philosopher thoughts",text = "To be or not to be..."))
        myNotes.add(Note(ownerId = user1.userId,title = "My Second Note",text = "I'm user1. This is my second note"))
        myNotes.add(Note(ownerId = user1.userId,title = "When your neighbour is philosopher",text = "I met user2 yesterday..."))

        // Assert -> q-ty of notes by user1 is 3
        assertEquals(0x0003,myNotes.get(user1)?.size)
    }

    @Test
    fun getNoteById() {

        // Arrange -> prepare variables
        val user1 = User(0x0001)
        var myNotes = Entities<Note>()
        val aWord = "philosopher"

        // Act -> add notes
        myNotes.add(Note(ownerId = user1.userId,title = "My First Note",text = "I'm user1. This is my first note"))
        myNotes.add(Note(ownerId = user1.userId, title = "My Second Note", text = "I'm still user1. This is my second note"))
        myNotes.add(Note(ownerId = user1.userId, title = "When your neighbour is philosopher", text = "I met user2 yesterday..."))

        // Assert -> note with specific id contains specific word
        assertTrue(myNotes.getById(0x0003).title.contains(aWord))
    }

    @Test
    fun getFriendsNotes() {

        // Arrange -> prepare variables
        val user2 = User(0x0002)
        val user3 = User(0x0003)
        val user1 = User(0x0001,friends = listOf(user2, user3))
        var myNotes = Entities<Note>()

        // Act -> add notes
        myNotes.add(Note(ownerId = user2.userId,title = "Philosopher thoughts",text = "To be or not to be..."))
        myNotes.add(Note(ownerId = user3.userId, title = "Building future together!", text = "I'm just back from shop..."))
        myNotes.add(Note(ownerId = user1.userId, title = "My First Note", text = "I'm user1. This is my first note"))
        myNotes.add(Note(ownerId = user1.userId, title = "My Second Note", text = "I'm still user1. This is my second note"))
        myNotes.add(Note(ownerId = user1.userId, title = "When your neighbour is philosopher", text = "I met user2 yesterday..."))

        // Assert -> note with specific id contains specific word
        assertEquals(0x0002,myNotes.getFriendsNotes(user1)?.size)
    }

    @Test
    fun createNotesComment() {
        // Arrange -> prepare variables and the rest
        val user1 = User(0x0008)
        var myNotes  = Entities<Note>()
        var myNote = myNotes.add(Note(ownerId = user1.userId, title = "Dance with me", text = "Strangers in the night..."))

        // Act -> add comments to note
        var lastCommentId = myNotes.createComment(myNote,Comment(message = "Your note reminded me when I was on vacation.."))

        // Assert -> last comment id is as expected
        assertEquals(0x0001,lastCommentId)
    }

    @Test
    fun getNotesComments() {

        // Arrange -> prepare variables and the rest
        val user1 = User(0x0004)
        var myNotes  = Entities<Note>()
        var myNote = myNotes.add(Note(ownerId = user1.userId, title = "Dance with me", text = "Strangers in the night..."))

        // Act -> add comments to note
        myNotes.createComment(myNote,Comment(message = "Your note reminded me when I was on vacation.."))
        myNotes.createComment(myNote,Comment(message = "Call me!! I have an offer for you that you can't decline!"))

        // Assert -> last comment id is as expected
        assertEquals(0x0002,myNotes.getComments(myNote)?.size)
    }

    @Test
    fun deleteNote() {

        // Arrange -> prepare variables and the rest
        val user1 = User(0x0004)
        var myNotes  = Entities<Note>()
        var myNote = myNotes.add(Note(ownerId = user1.userId, title = "Dance with me", text = "Strangers in the night..."))

        // Act -> add comments to note
        myNotes.createComment(myNote,Comment(message = "Your note reminded me when I was on vacation.."))
        myNotes.createComment(myNote,Comment(message = "Call me!! I have an offer for you that you can't decline!"))

        myNotes.delete(myNote)

        // Assert -> last comment id is as expected
        assertEquals(null,myNotes.get(user1))
    }

    @Test (expected = CommentNotFoundException::class)
    fun deleteNotesComment() {

        // Arrange -> prepare variables and the rest
        val user1 = User(0x0008)
        var myNotes  = Entities<Note>()
        var myNote = myNotes.add(Note(ownerId = user1.userId, title = "Dance with me", text = "Strangers in the night..."))

        // Act -> add comments to note
        var lastCommentId = myNotes.createComment(myNote,Comment(message = "Your note reminded me when I was on vacation.."))

        myNotes.deleteComment(myNote,lastCommentId)
        myNotes.editComment(myNote,lastCommentId)
    }

    @Test (expected = CommentNotFoundException::class)
    fun restoreCommentFailedNotExist() {

        // Arrange -> prepare variables and the rest
        val user1 = User(0x0008)
        var myNotes  = Entities<Note>()
        var myNote = myNotes.add(Note(ownerId = user1.userId, title = "Dance with me", text = "Strangers in the night..."))

        // Act -> initiate non-existing comment
        var nonExistingComment = 0x0100

        myNotes.restoreComment(nonExistingComment)
    }

    @Test
    fun restoreCommentSuccess() {

        // Arrange -> prepare variables and the rest
        val user1 = User(0x0008)
        var myNotes  = Entities<Note>()
        var myNote = myNotes.add(Note(ownerId = user1.userId, title = "Dance with me", text = "Strangers in the night..."))

        // Act -> add comments to note
        var firstCommentId = myNotes.createComment(myNote,Comment(message = "First comment... "))
        var lastCommentId = myNotes.createComment(myNote,Comment(message = "Second comment..."))

        assertEquals(0x0002,myNotes.getComments(myNote)?.size)
        myNotes.deleteComment(myNote,firstCommentId)
        assertEquals(0x0001,myNotes.getComments(myNote)?.size)
        myNotes.restoreComment(firstCommentId)
        assertEquals(0x0002,myNotes.getComments(myNote)?.size)
    }
}
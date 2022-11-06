package ru.netology

class Entities <T : Entity>{

    private var entities = ArrayList<T>()
    private var comments = ArrayList<Comment>()

    fun add(entity: T) : T {
        entity.id = if (entities.isEmpty()) 0x0001 else entities.last().id + 1
        entities += entity
        return entities.last()
    }
 
    fun get(user: User): List<T>? {
        var userOwnedEntities = emptyList<T>()
        for (entity in entities) {
            if (entity.ownerId == user.userId && !entity.isDeleted) userOwnedEntities += entity
        }
        return userOwnedEntities.ifEmpty { null }
    }
    
    fun getById(id: Int): T {
        for (entity in entities) {
            if (entity.id == id && !entity.isDeleted) return entity
        }
        throw NoteNotFoundException(id)
    }

    fun getFriendsNotes(user: User): List<T>? {
        var userFriendsOwnedEntities = emptyList<T>()
        if (user.friends.isEmpty()) return null else {
            for (friend in user.friends) {
                userFriendsOwnedEntities += get(friend)?: emptyList()
            }
        }
        return userFriendsOwnedEntities
    }

    fun getComments(entity: T): List<Comment>? {
        var entityComments = emptyList<Comment>()
        if (entity.isDeleted ) return null else {
            for ((index, comment) in comments.withIndex()) {
                if (comment.noteId == entity.id && !comment.isDeleted) entityComments += comments[index]
            }
        }
        return entityComments
    }

    fun createComment(entity: T, comment: Comment) : Int {
        comment.id = if (comments.isEmpty()) 0x0001 else comments.last().id + 1
        comment.noteId=entity.id
        comments += comment
        return comments.last().id
    }

    fun delete(entity: T) {
        for (comment in comments) {
            if (comment.ownerId == entity.id && !comment.isDeleted) comment.isDeleted = true
        }
        for (candidateEntity in entities) {
            if (candidateEntity.id == entity.id && !candidateEntity.isDeleted) {
                candidateEntity.isDeleted = true
                return
            }
        }
        throw NoteNotFoundException(entity.id)
    }

    fun deleteComment(entity: T, commentId: Int) {
        for (candidateComment in comments) {
            if (candidateComment.id == commentId && candidateComment.noteId == entity.id && !candidateComment.isDeleted) {
                candidateComment.isDeleted = true
                return
            }
        }
        throw CommentNotFoundException(commentId)
    }

    fun edit(entity: T) {
        for ((index,candidateEntity) in entities.withIndex()) {
            if (candidateEntity.id == entity.id && !candidateEntity.isDeleted) {
                entities[index]=entity
                return
            }
        }
        throw NoteNotFoundException(entity.id)
    }

    fun editComment(entity: T, commentId: Int) {
        for ((index, candidateComment) in comments.withIndex()) {
            if (candidateComment.id == commentId && candidateComment.noteId == entity.id && !candidateComment.isDeleted) {
                comments[index]=candidateComment
                return
            }
        }
        throw CommentNotFoundException(commentId)
    }

    fun restoreComment(commentId: Int) {
        for (candidateComment in comments) {
            if (candidateComment.id == commentId && candidateComment.isDeleted) {
                candidateComment.isDeleted = false
                return
            }
        }
        throw CommentNotFoundException(commentId)
    }
}
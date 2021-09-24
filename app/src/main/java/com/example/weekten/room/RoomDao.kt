package com.example.weekten.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface RoomDao {

    /*Add Post to Database*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPost(post: PostEntity)

    /*Read all Posts in the Database*/
    @Query("SELECT * FROM post_table")
    fun readAllPost(): Flow<List<PostEntity>>

    /*Add Comment to Database*/
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addComment(comment: CommentEntity)

    /*Read all Posts in the Database*/
    @Query("SELECT * FROM comment_table")
    fun readAllComments(): Flow<List<CommentEntity>>

    /*Read a Post Comments*/
    @Query("SELECT * FROM comment_table WHERE postId = :postId ORDER BY postId DESC")
    fun readComments(postId: Int): Flow<List<CommentEntity>>


}
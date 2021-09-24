package com.example.weekten.repository

import com.example.weekten.model.Comment
import com.example.weekten.model.Post
import com.example.weekten.utils.Resource
import kotlinx.coroutines.flow.flow
import java.util.concurrent.Flow

interface IRepository {
    //get all post

    suspend fun getPosts(): kotlinx.coroutines.flow.Flow<Resource<List<Post>>>

    suspend fun addPost(post: Post)

    suspend fun getComments(postId: Int): kotlinx.coroutines.flow.Flow<Resource<List<Comment>>>

    suspend fun getAllComments(): kotlinx.coroutines.flow.Flow<Resource<List<Comment>>>

    suspend fun pushComment(comment: Comment)
}
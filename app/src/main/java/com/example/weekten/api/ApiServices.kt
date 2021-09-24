package com.example.weekten.api

import com.example.weekten.model.Comment
import com.example.weekten.model.Post
import com.example.weekten.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

    // fetch post
    @GET(Constants.FETCH_POST_END_POINT)
    suspend fun getPosts(): List<Post>

//fetch all comment
    @GET("comments")
    suspend fun getAllComments(): List<Comment>

//    @GET(Constants.FETCH_COMMENT_END_POINT)
//    suspend fun getComment(@Path ("postNumber") postNumber : Int) :List<Comment>
}
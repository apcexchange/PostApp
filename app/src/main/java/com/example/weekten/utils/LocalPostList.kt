package com.example.weekten.utils

import com.example.weekten.model.Comment
import com.example.weekten.model.Post

object LocalPostList {
    private var localPostList: MutableList<Post> = ArrayList()
    private var localCommentList: MutableList<Comment> = ArrayList()

    fun getPostList(): List<Post> {
        return localPostList
    }

    fun getCommentList(): List<Comment> {
        return localCommentList
    }
}
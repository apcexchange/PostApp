package com.example.weekten.repository

import android.util.Log
import android.widget.Toast
import com.example.weekten.api.ApiServices
import com.example.weekten.model.Comment
import com.example.weekten.model.Post
import com.example.weekten.room.CachedCommentMapper
import com.example.weekten.room.CachedPostMapper
import com.example.weekten.room.RoomDao
import com.example.weekten.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class Repository constructor(
    private val roomDao: RoomDao,
    private val cachedPostMapper: CachedPostMapper,
    private val cachedCommentMapper: CachedCommentMapper,
    private val api:ApiServices
) :IRepository{

    //get all post
    override suspend fun getPosts(): Flow<Resource<List<Post>>> = flow{
        emit(Resource.Loading)
        try {
            //retrieve post
            val remotePost = api.getPosts()
            /*Map posts to Local Database*/
            for (post in remotePost){
                roomDao.addPost(cachedPostMapper.mapToEntity(post))
            }
            //retrieve post from database
            val catchedPost = roomDao.readAllPost()
            catchedPost.collect {
                emit(Resource.Success(cachedPostMapper.mapFromEntityList(it)))
            }
        }catch (e:Exception){

        }
    }

    override suspend fun addPost(post: Post) {
        try {
            /*Add Comment to Local Database*/
            roomDao.addPost(cachedPostMapper.mapToEntity(post))
        }catch (e:Exception){
            Log.d("PPP", "addPost: ${e.message}")
        }
    }

    override suspend fun getComments(postId: Int): Flow<Resource<List<Comment>>>  = flow{
        emit(Resource.Loading)
        try {
            //retrieve post from db
            val cachedComments = roomDao.readComments(postId)
            cachedComments.collect{
                emit(Resource.Success(cachedCommentMapper.mapFromEntityList(it)))
            }
        }catch (e:Exception){
            emit(Resource.Error(e))
        }

    }

    //fetching all comment
    override suspend fun getAllComments(): Flow<Resource<List<Comment>>>  = flow{
        emit(Resource.Loading)
        try {
            //get all network comments
            val networkComment = api.getAllComments()
            //map comment to db
            for (comment in networkComment){
                roomDao.addComment(cachedCommentMapper.mapToEntity(comment))
            }
            //retrieved Comment from db
            val retrievedComment = roomDao.readAllComments()
            retrievedComment.collect {
                emit(Resource.Success(cachedCommentMapper.mapFromEntityList(it)))
            }
        }catch (e:Exception){
            emit(Resource.Error(e))
        }
    }

    override suspend fun pushComment(comment: Comment) {
        //add comment
        try {
            roomDao.addComment(cachedCommentMapper.mapToEntity(comment))
        }catch (e:Exception){
            Log.d("PPP", "pushComment: ${e.message}")
        }
    }


}
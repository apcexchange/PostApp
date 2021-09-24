package com.example.weekten.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weekten.model.Comment
import com.example.weekten.model.Post
import com.example.weekten.repository.Repository
import com.example.weekten.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.w3c.dom.Text
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (val repository: Repository): ViewModel(){

    private var _postList :MutableLiveData<Resource<List<Post>>> = MutableLiveData()
    val postList :LiveData<Resource<List<Post>>> get() = _postList

    private var _commentList = MutableLiveData<Resource<List<Comment>>>()
    val commentList: LiveData<Resource<List<Comment>>> get() = _commentList

    private var _entireCommentList = MutableLiveData<Resource<List<Comment>>>()
    val entireCommentList: LiveData<Resource<List<Comment>>> get() = _entireCommentList

    init {
        getPosts()
        getAllComments()
    }

    private fun getPosts(){
        viewModelScope.launch {
            _postList.value = Resource.Loading
            val response = repository.getPosts()
            response.collect {
                _postList.value = it
            }
        }
    }

    fun addNewPost(post: Post){
        viewModelScope.launch {
            repository.addPost(post)
        }
    }

    //get all comment
    fun getAllComments(){
        viewModelScope.launch {
            _entireCommentList.postValue(Resource.Loading)
            val response = repository.getAllComments()
            response.collect {
                _entireCommentList.postValue(it)
            }
        }
    }

    /*Function to get Comments*/
    fun getComments(postId: Int) {
        viewModelScope.launch {
            _commentList.postValue(Resource.Loading)
            val response = repository.getComments(postId)
            response.collect {
                _commentList.value = it
            }
        }
    }

    /*Function to add Comments*/
    fun pushComment(comment: Comment) {
        viewModelScope.launch {
            Resource.Loading
            repository.pushComment(comment)
        }
    }

    //search post
    private var cachedPostList = MutableLiveData<Resource<List<Post>>>()
    private var isSearchStarted = true
    var isSearching = MutableStateFlow(false)

    fun searchFromListOfPost(query:String){
        if (isSearchStarted){
            cachedPostList.value = _postList.value
            isSearchStarted = false
        }

        val listenToWordTyped = if (isSearchStarted){
            postList.value
        }else{
            cachedPostList.value
        }

        viewModelScope.launch {
            if (query.isEmpty()){
                _postList.value = cachedPostList.value
                isSearching.value = false
                isSearchStarted = true
                return@launch
            }else{
                val results = listenToWordTyped?.data?.filter {
                    it.title.contains(query.trim(),true) ||
                            it.body.contains(query.trim(),true) ||
                            it.id.toString().contains(query.trim())
                }
                results?.let {
                    _postList.value = Resource.Success(results)
                }
            }

            if (isSearchStarted){
                cachedPostList.value = _postList.value
                isSearchStarted = false
            }
            isSearching.value = true
        }
    }


}
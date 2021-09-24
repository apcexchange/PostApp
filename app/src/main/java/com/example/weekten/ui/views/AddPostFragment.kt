package com.example.weekten.ui.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.weekten.R
import com.example.weekten.databinding.FragmentAddPostBinding
import com.example.weekten.model.Post
import com.example.weekten.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPostFragment : Fragment(R.layout.fragment_add_post) {

    private var _binding: FragmentAddPostBinding? = null
    private val binding get() = _binding!!
     val viewModel: MainViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addPost()
    }

    private fun addPost() {
        binding.btPost.setOnClickListener {
            val postTitle = binding.newPostTitle.text.toString()
            val postBody = binding.newPostBody.text.toString()
            val userId = (1..10).random()
            val id = 0

            when {
                postTitle.isEmpty() -> {
                    binding.newPostTitle.error = "Title can't be empty"
                    return@setOnClickListener
                }
                postBody.isEmpty() -> {
                    binding.newPostBody.error = " body can't be empty"
                    return@setOnClickListener
                }
                else -> {
                    val newPost = Post(userId,id,postTitle,postBody)
                    viewModel.addNewPost(newPost)
                    activity?.onBackPressed()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}
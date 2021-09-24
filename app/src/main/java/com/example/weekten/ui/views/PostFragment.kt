package com.example.weekten.ui.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weekten.R
import com.example.weekten.databinding.FragmentPostBinding
import com.example.weekten.model.Post
import com.example.weekten.room.LocalDatabase
import com.example.weekten.ui.adapter.PostAdapter
import com.example.weekten.utils.LocalPostList.getPostList
import com.example.weekten.utils.Resource
import com.example.weekten.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostFragment : Fragment(R.layout.fragment_post), PostAdapter.PassDataToNextFragment {

    private var _binding: FragmentPostBinding? =null
   private val binding get() = _binding!!
    private lateinit var postRvAdapter: PostAdapter

    private val viewModel:MainViewModel by viewModels()
    private var localPostList = getPostList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPostBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        swipeRefresh()
        loadPosts()
        addPost()
        searchPost()
    }

    private fun searchPost() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.searchFromListOfPost(it) }
                return false
            }
        })
    }

    private fun swipeRefresh() {
        /*Set-up Rv Swipe to Refresh*/
        binding.swipeRefresh.setOnRefreshListener {
            loadPosts()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun addPost() {
        binding.fabPost.setOnClickListener {
            val action = PostFragmentDirections.actionPostFragmentToAddPostFragment()
            findNavController().navigate(action)
        }
    }

    private fun setUpRecyclerView() {
        binding.rvPost.apply {
            layoutManager = LinearLayoutManager(requireContext())
            postRvAdapter = PostAdapter(this@PostFragment)
            adapter = postRvAdapter
        }
        /*Scroll to Position of New Post*/
        postRvAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                binding.rvPost.scrollToPosition(positionStart)
            }
        })
    }

    private fun loadPosts() {
        viewModel.postList.observe(viewLifecycleOwner, Observer { result->
            when (result){
                is Resource.Success ->{
                    //add to recyclerView
                    result.data.let {
                        if (it != null) {
                            postRvAdapter.submitList(it)
                            localPostList = it as MutableList<Post>
                        }
                        hideProgressBar()
                    }
                }
                is Resource.Loading ->{
                    showProgressBar()
                }
                is Resource.Error ->{
                    hideProgressBar()
                    Toast.makeText(requireContext(),"Could not fetch Post",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun onItemSelected(position: Int, item: Post) {
//        val currentPost = localPostList[position]
        val action = PostFragmentDirections.actionPostFragmentToCommentFragment(position)
        findNavController().navigate(action)
    }

}
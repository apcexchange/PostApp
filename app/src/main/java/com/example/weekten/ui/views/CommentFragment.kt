package com.example.weekten.ui.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weekten.R
import com.example.weekten.databinding.FragmentCommentBinding
import com.example.weekten.model.Comment
import com.example.weekten.ui.adapter.CommentAdapter
import com.example.weekten.utils.LocalPostList.getCommentList
import com.example.weekten.utils.Resource
import com.example.weekten.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommentFragment : Fragment(R.layout.fragment_comment) {

    private var _binding: FragmentCommentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels()
    private lateinit var  commentRvAdapter : CommentAdapter
    private val args: CommentFragmentArgs by navArgs()
    private var localCommentList = getCommentList()

    var postId = 1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCommentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        loadComment()
        addComment()
        swipeToRefresh()
    }

    private fun swipeToRefresh() {
        binding.swipeRefreshComment.setOnRefreshListener {
            postId?.let {
                viewModel.getComments(it)
                loadComment()
                binding.swipeRefreshComment.isRefreshing = false
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.rvComments.apply {
            layoutManager = LinearLayoutManager(requireContext())
            commentRvAdapter = CommentAdapter()
            adapter = commentRvAdapter
        }


        //scroll to new comment position
        commentRvAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                binding.rvComments.scrollToPosition(positionStart)
            }
        })
    }
    private fun loadComment() {
        viewModel.getComments(postId)
        Toast.makeText(requireContext()," post Id number ${args.position + 1} selected",Toast.LENGTH_LONG).show()
        viewModel.commentList.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    response.data?.let {
                        // add to recyclerview
                        commentRvAdapter.submitList(it)
                        Log.d("COMMENT", "loadComment: $it")
                        localCommentList = it.toMutableList()
                    }
                    hideProgressBar()
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(),"could not fetch comment",Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun hideProgressBar() {
        binding.commentProgressBar.visibility = View.GONE
    }

    private fun showProgressBar(){
        binding.commentProgressBar.visibility = View.VISIBLE
    }

    private fun addComment() {
      binding.floatingActionButton.setOnClickListener {
            val action = CommentFragmentDirections.actionCommentFragmentToAddCommentFragment()
            findNavController().navigate(action)
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}
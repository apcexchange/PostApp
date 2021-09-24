package com.example.weekten.ui.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weekten.R
import com.example.weekten.databinding.FragmentAddCommentBinding
import com.example.weekten.databinding.FragmentAddPostBinding


class AddCommentFragment : Fragment() {

    private var _binding: FragmentAddCommentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


}
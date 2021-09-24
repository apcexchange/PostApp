package com.example.weekten.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weekten.R
import com.example.weekten.model.Post

class PostAdapter(private val passDataToNextFragment: PassDataToNextFragment? = null): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, diffCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PostViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.post_list_items,
                parent,
                false
            ),
            passDataToNextFragment
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is PostViewHolder ->{
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Post>) {
        differ.submitList(list)
    }


    class PostViewHolder constructor(
        itemView: View, private val passDataToNextFragment: PassDataToNextFragment?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Post) = with(itemView){
            itemView.setOnClickListener {
                passDataToNextFragment?.onItemSelected(adapterPosition,item)
            }
            val postTitle = itemView.findViewById<TextView>(R.id.tvTitle)
            val postBody = itemView.findViewById<TextView>(R.id.tvBody)
            val postId = itemView.findViewById<TextView>(R.id.tvId)

            postTitle.text = item.title
            postBody.text = item.body
            postId.text = item.id.toString()
        }
    }
        interface PassDataToNextFragment {
            fun onItemSelected(position: Int, item: Post)
        }

    }


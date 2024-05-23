package com.example.githubuser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.databinding.ItemUserBinding

class UserAdapter : ListAdapter<ItemsItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private var onItemClick: ((ItemsItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (ItemsItem) -> Unit) {
        onItemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }
    inner class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem){
            binding.tvItem.text = user.login
            Glide.with(binding.root.context)
            .load("${user.avatarUrl}")
            .into(binding.ivPicture)

            // Menambahkan listener klik ke itemView
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick?.invoke(user)
                }
            }
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
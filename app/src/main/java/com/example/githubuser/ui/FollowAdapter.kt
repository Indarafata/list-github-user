package com.example.githubuser.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.data.response.FollowResponseItem
import com.example.githubuser.databinding.ItemUserBinding

class FollowAdapter : ListAdapter<FollowResponseItem, FollowAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val users = getItem(position)
        holder.bind(users)
    }
    class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: FollowResponseItem){
            binding.tvItem.text = user.login
            Glide.with(binding.root.context)
                .load("${user.avatarUrl}")
                .into(binding.ivPicture)
        }
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FollowResponseItem>() {
            override fun areItemsTheSame(oldItem: FollowResponseItem, newItem: FollowResponseItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: FollowResponseItem, newItem: FollowResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
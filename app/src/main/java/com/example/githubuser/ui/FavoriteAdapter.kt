package com.example.githubuser.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.database.Favorite
import com.example.githubuser.databinding.ItemUserBinding
import com.example.githubuser.helper.FavoriteDiffCallback

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private val listFavorites = ArrayList<Favorite>()
    fun setListFavorite(listFavorite: List<Favorite>) {
        val diffCallback = FavoriteDiffCallback(this.listFavorites, listFavorite)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorites.clear()
        this.listFavorites.addAll(listFavorite)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorites[position])
    }
    override fun getItemCount(): Int {
        return listFavorites.size
    }
    inner class FavoriteViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            with(binding) {
                Glide.with(binding.root.context)
                    .load(favorite.ava_url)
                    .into(ivPicture)
                tvItem.text = favorite.username
                cvFavorite.setOnClickListener {
                    val intent = Intent(it.context, DetailUserActivity::class.java)
                    intent.putExtra("login", favorite.username)
                    intent.putExtra(DetailUserActivity.EXTRA_FAVORITE, favorite)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}
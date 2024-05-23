package com.example.githubuser.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.githubuser.database.Favorite

class FavoriteDiffCallback(private val oldFavoriteList: List<Favorite>, private val newFavotireList: List<Favorite>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavoriteList.size
    override fun getNewListSize(): Int = newFavotireList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteList[oldItemPosition].username == newFavotireList[newItemPosition].username
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavorite = oldFavoriteList[oldItemPosition]
        val newFavorite = newFavotireList[newItemPosition]
        return oldFavorite.username == newFavorite.username && oldFavorite.ava_url == newFavorite.ava_url
    }
}
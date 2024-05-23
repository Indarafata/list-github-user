package com.example.githubuser.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.repository.FavoriteRepository
import com.example.githubuser.database.Favorite

class FavoriteAddViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)
    fun insert(favorite: Favorite) {
        mFavoriteRepository.insert(favorite)
    }

    fun delete(favorite: Favorite) {
        mFavoriteRepository.delete(favorite)
    }
}
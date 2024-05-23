package com.example.githubuser.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.data.repository.FavoriteRepository
import com.example.githubuser.database.Favorite

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)
    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoriteRepository.getAllFavorites()
}
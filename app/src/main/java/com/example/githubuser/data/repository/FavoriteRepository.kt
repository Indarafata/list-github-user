package com.example.githubuser.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuser.database.Favorite
import com.example.githubuser.database.FavoriteDao
import com.example.githubuser.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoritesDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoritesDao = db.favoriteDao()
    }
    fun getAllFavorites(): LiveData<List<Favorite>> = mFavoritesDao.getAllUserFavorite()

    fun insert(favorite: Favorite) {
        executorService.execute { mFavoritesDao.insert(favorite) }
    }
    fun delete(favorite: Favorite) {
        executorService.execute { mFavoritesDao.delete(favorite) }
    }
}
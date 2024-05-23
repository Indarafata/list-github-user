package com.example.githubuser.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.ui.FavoriteViewModel
import com.example.githubuser.ui.insert.FavoriteAddViewModel

class ViewModelFactorys private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactorys? = null
        @JvmStatic
        fun getInstance(application: Application): ViewModelFactorys {
            if (INSTANCE == null) {
                synchronized(ViewModelFactorys::class.java) {
                    INSTANCE = ViewModelFactorys(application)
                }
            }
            return INSTANCE as ViewModelFactorys
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(FavoriteAddViewModel::class.java)) {
            return FavoriteAddViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
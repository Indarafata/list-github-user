package com.example.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.helper.ViewModelFactorys

class FavoriteActivity : AppCompatActivity() {
    private var _activityFavoriteBinding: ActivityFavoriteBinding? = null
    private val binding get() = _activityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val mainViewModel = obtainViewModel(this@FavoriteActivity)
        mainViewModel.getAllFavorites().observe(this) { favoriteList ->
            if (favoriteList != null) {
                adapter.setListFavorite(favoriteList)
            }
        }

        adapter = FavoriteAdapter()
        binding?.rvFavorite?.layoutManager = LinearLayoutManager(this)
        binding?.rvFavorite?.setHasFixedSize(true)
        binding?.rvFavorite?.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteBinding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactorys.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }
}
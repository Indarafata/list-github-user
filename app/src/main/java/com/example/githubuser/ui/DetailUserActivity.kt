package com.example.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.data.response.UserDetailResponse
import com.example.githubuser.data.retrofit.ApiConfig
import com.example.githubuser.database.Favorite
import com.example.githubuser.database.FavoriteDao
import com.example.githubuser.database.FavoriteRoomDatabase
import com.example.githubuser.databinding.DetailUserBinding
import com.example.githubuser.helper.ViewModelFactorys
import com.example.githubuser.ui.insert.FavoriteAddViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: DetailUserBinding

    //menambah favorite user
    private lateinit var favoriteAddViewModel: FavoriteAddViewModel
    private var favorite: Favorite? = null
    private var favoriteCheck: FavoriteDao? = null

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
        private const val TAG = "Detail activity"

        //menambah favorite user
        const val EXTRA_FAVORITE = "extra_favorite"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        supportActionBar?.elevation = 0f

        val username = intent.getStringExtra("login")
        val avatarUrl = intent.getStringExtra("avatar_url")
        val sectionsPagerAdapter = SectionPagerAdapter(this, username!!)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        val tabs: TabLayout = findViewById(R.id.tabs)

        viewPager.adapter = sectionsPagerAdapter

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()


        if (username != null) {
            getDetail(username)
        }

        //menambah favorite user
        favoriteAddViewModel = obtainViewModel(this@DetailUserActivity)
        favoriteCheck = FavoriteRoomDatabase.getDatabase(applicationContext).favoriteDao()
        favorite = intent.getParcelableExtra(EXTRA_FAVORITE)

        checkIsUserFavorite(username) { isFavorite ->
            if (isFavorite || favorite != null) {
                binding.btnAddFavorite.setImageResource(R.drawable.ic_favorite_full)
                //inisialisasi favorite supaya tidak null
                if(favorite == null) favorite = Favorite()
            }
            else {
                favorite = Favorite()
            }
        }

        binding?.btnAddFavorite?.setOnClickListener {
            when {
                else -> {
                    favorite.let { favorite ->
                        favorite?.username = username
                        favorite?.ava_url = avatarUrl
                        Log.d("sini", "hahahha")
                    }

                    checkIsUserFavorite(username) { isFavorite ->
                        if (isFavorite) {
                            binding.btnAddFavorite.setImageResource(R.drawable.ic_favorite_border)
                            favoriteAddViewModel.delete(favorite as Favorite)
                        }
                        else{
                            binding.btnAddFavorite.setImageResource(R.drawable.ic_favorite_full)
                            favoriteAddViewModel.insert(favorite as Favorite)
                        }
                    }
                }
            }
        }
    }

    private fun checkIsUserFavorite(username: String, callback: (Boolean) -> Unit) {
        favoriteCheck = FavoriteRoomDatabase.getDatabase(applicationContext).favoriteDao()
        val existingFavoriteLiveData = favoriteCheck?.getUserFavorite(username)

        existingFavoriteLiveData?.observe(this) { existingFavorite ->
            if (existingFavorite != null && existingFavorite.isNotEmpty()) {
                callback(true)
                //menghentikan observe supaya tidak terjadi perulangan
                existingFavoriteLiveData?.removeObservers(this)
            } else {
                callback(false)
                //menghentikan observe supaya tidak terjadi perulangan
                existingFavoriteLiveData?.removeObservers(this)
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteAddViewModel {
        val factory = ViewModelFactorys.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteAddViewModel::class.java)
    }

    private fun getDetail(name: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getDetailUsers(name)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setUserData(responseBody)
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t}")

            }
        })
    }

    private fun setUserData(userLogin: UserDetailResponse) {
        binding.ivUserName.text = userLogin.login
        binding.ivName.text = userLogin.name
        binding.ivFollower.text = "Followers ${userLogin.followers}"
        binding.ivFollowing.text = "Following ${userLogin.following}"
        try {
            Glide.with(this)
                .load(userLogin.avatarUrl)
                .into(binding.ivPicture)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progress.visibility = View.VISIBLE
        } else {
            binding.progress.visibility = View.GONE
        }
    }
}
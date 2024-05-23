package com.example.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.data.response.GitHubResponse
import com.example.githubuser.data.response.ItemsItem
import com.example.githubuser.data.retrofit.ApiConfig
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.preference.SettingPreferences
import com.example.githubuser.preference.dataStore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        supportActionBar?.hide()
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        //menutup SearchView saat aplikasi pertama kali dijalankan
        binding.searchView.isIconified = true

        binding.searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.searchView.isIconified = false
            }
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrBlank()) {
                    findUser(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        val pref = SettingPreferences.getInstance(application.dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            MainViewModel::class.java
        )

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        findUser("A")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_form, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_theme -> {
                val intent = Intent(this, ThemesActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_favorit -> {
                val intent = Intent(this, FavoriteActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun findUser(query: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getGitHubUsers(query)
        client.enqueue(object : Callback<GitHubResponse> {
            override fun onResponse(
                call: Call<GitHubResponse>,
                response: Response<GitHubResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setUserData(responseBody.items)
                    }
                } else {
                    Log.e(TAG, "fail: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GitHubResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setUserData(userLogin: List<ItemsItem>) {
        val adapter = UserAdapter()

        adapter.submitList(userLogin)
        binding.rvUser.adapter = adapter
        
        adapter.setOnItemClickListener { userItem ->
            val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
            intent.putExtra("login", userItem.login)
            intent.putExtra("avatar_url", userItem.avatarUrl)
            startActivity(intent)
            Toast.makeText(this, "Klik pada ${userItem.login}", Toast.LENGTH_SHORT).show()
        }
    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
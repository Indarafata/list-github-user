package com.example.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.R
import com.example.githubuser.preference.SettingPreferences
import com.example.githubuser.preference.dataStore
import com.google.android.material.switchmaterial.SwitchMaterial

class ThemesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_themes)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            MainViewModel::class.java
        )

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            mainViewModel.saveThemeSetting(isChecked)
        }


//        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
//            if (isChecked) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                switchTheme.isChecked = true
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                switchTheme.isChecked = false
//            }
//        }
    }
}
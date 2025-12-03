package com.example.uts

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class UTSApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // (PENGUJIAN) Menonaktifkan logika tema dinamis untuk sementara
        /*
        val sharedPreferences = getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
        val isDarkMode = sharedPreferences.getBoolean("is_dark_mode", false)

        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        */
    }
}

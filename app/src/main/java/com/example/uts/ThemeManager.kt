package com.example.uts

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {

    private const val PREFS_NAME = "ThemePrefs"
    private const val KEY_THEME = "theme_mode"

    private const val THEME_LIGHT = 0
    private const val THEME_DARK = 1

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    // Fungsi untuk menerapkan tema yang tersimpan
    fun applyTheme(context: Context) {
        val mode = getPreferences(context).getInt(KEY_THEME, AppCompatDelegate.MODE_NIGHT_NO)
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    // Fungsi untuk mengecek apakah dark mode sedang aktif
    fun isDarkMode(context: Context): Boolean {
        return getPreferences(context).getInt(KEY_THEME, AppCompatDelegate.MODE_NIGHT_NO) == AppCompatDelegate.MODE_NIGHT_YES
    }

    // Fungsi untuk mengubah dan menyimpan tema
    fun setTheme(context: Context, isDarkMode: Boolean) {
        val mode = if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        getPreferences(context).edit().putInt(KEY_THEME, mode).apply()
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}

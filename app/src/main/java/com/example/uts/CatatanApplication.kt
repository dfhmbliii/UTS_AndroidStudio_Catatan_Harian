package com.example.uts

import android.app.Application

class CatatanApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        // Terapkan tema yang tersimpan setiap kali aplikasi dimulai
        ThemeManager.applyTheme(this)
    }
}

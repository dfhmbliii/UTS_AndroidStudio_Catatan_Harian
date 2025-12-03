package com.example.uts

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Activity pembuka yang tugasnya hanya mengecek status login
 * dan mengarahkan pengguna ke layar yang sesuai.
 */
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sessionManager = SessionManager(this)

        // Cek status login
        if (sessionManager.isLoggedIn()) {
            // Jika sudah login, langsung ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            // Kirim juga user ID dari sesi, karena MainActivity membutuhkannya
            intent.putExtra("USER_ID", sessionManager.getUserId())
            startActivity(intent)
        } else {
            // Jika belum login, ke LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Selesaikan SplashActivity agar tidak bisa dikembaliin oleh pengguna
        finish()
    }
}

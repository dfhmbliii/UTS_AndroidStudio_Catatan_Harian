package com.example.uts

import android.content.Context
import android.content.SharedPreferences

/**
 * Kelas untuk mengelola sesi login pengguna menggunakan SharedPreferences.
 */
class SessionManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("app_session", Context.MODE_PRIVATE)

    companion object {
        const val USER_ID = "user_id"
        const val IS_LOGGED_IN = "is_logged_in"
    }

    /**
     * Menyimpan sesi login pengguna.
     * @param userId ID pengguna yang akan disimpan.
     */
    fun saveSession(userId: Int) {
        val editor = prefs.edit()
        editor.putInt(USER_ID, userId)
        editor.putBoolean(IS_LOGGED_IN, true)
        editor.apply()
    }

    /**
     * Mengambil ID pengguna yang sedang login.
     * @return ID pengguna, atau -1 jika tidak ada yang login.
     */
    fun getUserId(): Int {
        return prefs.getInt(USER_ID, -1)
    }

    /**
     * Mengecek apakah ada pengguna yang sedang login.
     * @return true jika sudah login, false jika belum.
     */
    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(IS_LOGGED_IN, false)
    }

    /**
     * Menghapus sesi login (logout).
     */
    fun logout() {
        val editor = prefs.edit()
        editor.clear()
        editor.apply()
    }
}

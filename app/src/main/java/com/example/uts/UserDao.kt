package com.example.uts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.OnConflictStrategy

@Dao
interface UserDao {
    // 1. Fungsi untuk Register User (INSERT)
    // OnConflictStrategy.IGNORE: Jika username sudah ada, abaikan
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun registerUser(user: User): Long

    // 2. Fungsi untuk Login (SELECT)
    @Query("SELECT * FROM tb_user WHERE username = :username AND password = :password LIMIT 1")
    suspend fun loginUser(username: String, password: String): User?

    // 3. Fungsi untuk Cek Username sudah ada (untuk Register)
    // PERBAIKAN: Query harus 'SELECT *' agar bisa mengembalikan objek User yang lengkap.
    // Sebelumnya hanya 'SELECT id', sehingga Room tidak bisa membuat objek User.
    @Query("SELECT * FROM tb_user WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?
}
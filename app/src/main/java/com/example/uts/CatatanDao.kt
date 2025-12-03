package com.example.uts

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CatatanDao {

    // PERUBAHAN: Hanya mengambil catatan milik pengguna tertentu
    @Query("SELECT * FROM tb_catatan WHERE userId = :userId ORDER BY isPinned DESC, tanggal DESC")
    fun getCatatanByUser(userId: String): Flow<List<Catatan>>

    // PERUBAHAN: Mengambil satu catatan dan memastikan itu milik pengguna
    @Query("SELECT * FROM tb_catatan WHERE id = :id AND userId = :userId")
    fun getCatatanById(id: Int, userId: String): Flow<Catatan?>

    // PERUBAHAN: Pencarian juga dibatasi untuk pengguna tertentu
    @Query("SELECT * FROM tb_catatan WHERE userId = :userId AND (judul LIKE :query OR isi LIKE :query) ORDER BY isPinned DESC, tanggal DESC")
    fun searchCatatan(userId: String, query: String): Flow<List<Catatan>>

    @Insert
    suspend fun insert(catatan: Catatan)

    @Update
    suspend fun update(catatan: Catatan)

    @Delete
    suspend fun delete(catatan: Catatan)
}

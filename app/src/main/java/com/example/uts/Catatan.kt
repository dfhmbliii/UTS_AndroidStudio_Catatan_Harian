package com.example.uts

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_catatan")
data class Catatan(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // PERUBAHAN: Menambahkan ID pengguna yang memiliki catatan ini
    val userId: String,

    val judul: String,
    val isi: String,
    val tanggal: Long,
    val isPinned: Boolean = false
)

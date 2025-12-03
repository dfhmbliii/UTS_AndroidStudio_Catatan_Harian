package com.example.uts

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_user")
data class User(
    // id sebagai Primary Key dan dibuat otomatis
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // username dan password sebagai kolom tabel
    val username: String,
    val password: String
)

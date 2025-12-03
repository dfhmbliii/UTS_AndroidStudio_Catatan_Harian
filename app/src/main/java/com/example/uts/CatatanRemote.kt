package com.example.uts

import com.google.gson.annotations.SerializedName

data class CatatanRemote(
    @SerializedName("id") val id: Int?,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("judul") val judul: String,
    @SerializedName("isi") val isi: String,
    @SerializedName("tanggal") val tanggal: Long?,

    // PERBAIKAN FINAL (BERDASARKAN LOG): Diubah ke Int? sesuai output JSON
    @SerializedName("is_pinned") val isPinned: Int? = 0
)

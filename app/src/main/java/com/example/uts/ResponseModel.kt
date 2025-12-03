package com.example.uts

import com.google.gson.annotations.SerializedName

data class ResponseModel<T>(
    // PERBAIKAN FINAL (BERDASARKAN LOG): Diubah kembali ke Boolean sesuai output server
    @SerializedName("status")
    val status: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: T? = null
)

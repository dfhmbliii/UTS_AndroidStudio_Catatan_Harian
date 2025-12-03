package com.example.uts

import com.google.gson.annotations.SerializedName
data class UserResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("username")
    val username: String
    // Kita tidak perlu password dari server
)

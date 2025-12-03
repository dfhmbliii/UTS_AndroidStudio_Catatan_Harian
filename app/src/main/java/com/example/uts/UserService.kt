package com.example.uts

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
interface UserService {
    @FormUrlEncoded // Penting: karena kita mengirim data POST dengan format form (sesuai PHP $_POST)
    @POST("register.php")
    suspend fun registerUser(
        @Field("username") username: String,
        @Field("password") password: String
    ): ResponseModel<UserResponse> // Mengharapkan ResponseModel yang membungkus data UserResponse

    @FormUrlEncoded
    @POST("login.php")
    suspend fun loginUser(
        @Field("username") username: String,
        @Field("password") password: String
    ): ResponseModel<UserResponse> // Mengembalikan respons dengan data User ID dan username
}
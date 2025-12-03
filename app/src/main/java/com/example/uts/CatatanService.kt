package com.example.uts

import retrofit2.http.*
interface CatatanService {
    // 1. Tampil Daftar / Pencarian (GET)
    @GET("catatan_list.php")
    suspend fun getCatatan(
        @Query("user_id") userId: Int,
        @Query("query") query: String? = null // Query opsional untuk pencarian
    ): ResponseModel<List<CatatanRemote>> // Mengembalikan daftar CatatanRemote

    // 2. Tambah Catatan (POST - Action: tambah)
    @FormUrlEncoded
    @POST("catatan_manage.php")
    suspend fun tambahCatatan(
        @Field("action") action: String = "tambah",
        @Field("user_id") userId: Int,
        @Field("judul") judul: String,
        @Field("isi") isi: String
    ): ResponseModel<Any> // Hanya perlu status dan pesan

    // 3. Edit Catatan (POST - Action: edit)
    @FormUrlEncoded
    @POST("catatan_manage.php")
    suspend fun editCatatan(
        @Field("action") action: String = "edit",
        @Field("catatan_id") catatanId: Int,
        @Field("user_id") userId: Int,
        @Field("judul") judul: String,
        @Field("isi") isi: String
    ): ResponseModel<Any>

    // 4. Hapus Catatan (POST - Action: hapus)
    @FormUrlEncoded
    @POST("catatan_manage.php")
    suspend fun hapusCatatan(
        @Field("action") action: String = "hapus",
        @Field("catatan_id") catatanId: Int,
        @Field("user_id") userId: Int
    ): ResponseModel<Any>

    // 5. PENAMBAHAN: Sematkan / Lepas Sematan Catatan (POST - Action: toggle_pin)
    @FormUrlEncoded
    @POST("catatan_manage.php")
    suspend fun togglePinStatus(
        @Field("action") action: String = "toggle_pin",
        @Field("catatan_id") catatanId: Int,
        @Field("user_id") userId: Int,
        @Field("is_pinned") isPinned: Int // Kirim 1 untuk disematkan, 0 untuk dilepas
    ): ResponseModel<Any>
}

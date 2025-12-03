package com.example.uts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// PERUBAHAN: ViewModel sekarang menerima userId saat dibuat
class CatatanViewModel(private val dao: CatatanDao, private val userId: String) : ViewModel() {

    // PERUBAHAN: Mengambil catatan hanya untuk pengguna yang login
    val semuaCatatan: LiveData<List<Catatan>> = dao.getCatatanByUser(userId).asLiveData()

    // PERUBAHAN: Mengambil satu catatan dan memastikan itu milik pengguna
    fun getCatatanById(id: Int): LiveData<Catatan?> {
        return dao.getCatatanById(id, userId).asLiveData()
    }

    // PERUBAHAN: Pencarian juga dibatasi untuk pengguna tertentu
    fun searchCatatan(query: String): LiveData<List<Catatan>> {
        return dao.searchCatatan(userId, "%$query%").asLiveData()
    }

    // Fungsi insert tidak perlu userId karena akan ada di objek Catatan
    fun insert(catatan: Catatan) = viewModelScope.launch {
        dao.insert(catatan)
    }

    fun update(catatan: Catatan) = viewModelScope.launch {
        dao.update(catatan)
    }

    fun delete(catatan: Catatan) = viewModelScope.launch {
        dao.delete(catatan)
    }
}

// PERUBAHAN: "Pabrik" ViewModel sekarang juga membutuhkan userId
class CatatanViewModelFactory(
    private val catatanDao: CatatanDao,
    private val userId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CatatanViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CatatanViewModel(catatanDao, userId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

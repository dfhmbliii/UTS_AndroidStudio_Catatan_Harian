package com.example.uts

import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CatatanDetailActivity : AppCompatActivity() {

    private lateinit var etJudul: EditText
    private lateinit var etIsi: EditText
    private lateinit var btnSimpan: MaterialButton
    private lateinit var btnHapus: MaterialButton

    private var currentCatatan: Catatan? = null
    private val auth = Firebase.auth

    // PERUBAHAN: ViewModel sekarang dibuat dengan userId
    private val catatanViewModel: CatatanViewModel by viewModels {
        val userId = auth.currentUser?.uid ?: throw IllegalStateException("User tidak ditemukan")
        CatatanViewModelFactory((application as CatatanApplication).database.catatanDao(), userId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catatan_detail)

        etJudul = findViewById(R.id.etDetailJudul)
        etIsi = findViewById(R.id.etDetailIsi)
        btnSimpan = findViewById(R.id.btnSimpan)
        btnHapus = findViewById(R.id.btnHapus)

        val catatanId = intent.getIntExtra("CATATAN_ID", -1)

        if (catatanId != -1) {
            title = "Edit Catatan"
            catatanViewModel.getCatatanById(catatanId).observe(this) { catatan ->
                if (catatan != null) {
                    currentCatatan = catatan
                    etJudul.setText(catatan.judul)
                    etIsi.setText(catatan.isi)
                } else {
                    // Jika catatan tidak ditemukan (mungkin milik user lain), tutup activity
                    Toast.makeText(this, "Catatan tidak ditemukan.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        } else {
            title = "Catatan Baru"
            btnHapus.visibility = android.view.View.GONE
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnSimpan.setOnClickListener {
            handleSimpan()
        }

        btnHapus.setOnClickListener {
            handleHapus()
        }
    }

    private fun handleSimpan() {
        val judul = etJudul.text.toString().trim()
        val isi = etIsi.text.toString().trim()
        val userId = auth.currentUser?.uid

        if (userId == null) {
            Toast.makeText(this, "Sesi berakhir. Silakan login kembali.", Toast.LENGTH_LONG).show()
            // Optional: Arahkan ke LoginActivity
            return
        }

        if (judul.isEmpty()) {
            Toast.makeText(this, "Judul tidak boleh kosong.", Toast.LENGTH_SHORT).show()
            return
        }

        if (currentCatatan != null) {
            val catatanDiperbarui = currentCatatan!!.copy(
                judul = judul,
                isi = isi,
                tanggal = System.currentTimeMillis()
                // userId tidak perlu diubah karena tetap milik user yang sama
            )
            catatanViewModel.update(catatanDiperbarui)
            Toast.makeText(this, "Catatan berhasil diperbarui", Toast.LENGTH_SHORT).show()
        } else {
            // PERUBAHAN: Sertakan userId saat membuat catatan baru
            val catatanBaru = Catatan(
                userId = userId,
                judul = judul,
                isi = isi,
                tanggal = System.currentTimeMillis()
            )
            catatanViewModel.insert(catatanBaru)
            Toast.makeText(this, "Catatan berhasil disimpan", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    private fun handleHapus() {
        currentCatatan?.let {
            catatanViewModel.delete(it)
            Toast.makeText(this, "Catatan berhasil dihapus", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

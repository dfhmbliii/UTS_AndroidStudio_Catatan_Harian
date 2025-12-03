package com.example.uts

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: CatatanAdapter
    private lateinit var searchView: SearchView
    private lateinit var auth: FirebaseAuth

    private val catatanViewModel: CatatanViewModel by viewModels {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            handleLogout()
            throw IllegalStateException("User ID tidak ditemukan, sesi diakhiri.")
        }
        CatatanViewModelFactory((application as CatatanApplication).database.catatanDao(), userId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        if (auth.currentUser == null) {
            handleLogout()
            return
        }

        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val rvCatatan = findViewById<RecyclerView>(R.id.rvCatatan)
        searchView = findViewById(R.id.searchViewCatatan)
        val fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)

        adapter = CatatanAdapter(
            onClick = { catatan ->
                val intent = Intent(this, CatatanDetailActivity::class.java)
                intent.putExtra("CATATAN_ID", catatan.id)
                startActivity(intent)
            },
            onLongClick = { catatan ->
                showOptionsDialog(catatan)
            }
        )
        rvCatatan.layoutManager = LinearLayoutManager(this)
        rvCatatan.adapter = adapter

        catatanViewModel.semuaCatatan.observe(this) { daftarCatatan ->
            adapter.submitList(daftarCatatan)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = true

            override fun onQueryTextChange(newText: String?): Boolean {
                handleSearch(newText)
                return true
            }
        })

        fabAdd.setOnClickListener {
            val intent = Intent(this, CatatanDetailActivity::class.java)
            startActivity(intent)
        }
    }
    
    private fun handleSearch(query: String?) {
        val searchQuery = query?.trim()
        if (searchQuery.isNullOrEmpty()) {
            catatanViewModel.semuaCatatan.observe(this) { adapter.submitList(it) }
        } else {
            catatanViewModel.searchCatatan(searchQuery).observe(this) { adapter.submitList(it) }
        }
    }

    private fun showOptionsDialog(catatan: Catatan) {
        val isCurrentlyPinned = catatan.isPinned
        val pinOptionText = if (isCurrentlyPinned) "Lepas Sematan" else "Sematkan Catatan"

        val options = arrayOf(pinOptionText, "Hapus Catatan")

        AlertDialog.Builder(this)
            .setTitle("Pilih Aksi")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> { // Opsi Sematkan / Lepas Sematan
                        val catatanBaru = catatan.copy(isPinned = !isCurrentlyPinned)
                        catatanViewModel.update(catatanBaru)
                    }
                    1 -> { // Opsi Hapus
                        showDeleteConfirmationDialog(catatan)
                    }
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }
    
    private fun showDeleteConfirmationDialog(catatan: Catatan) {
        AlertDialog.Builder(this)
            .setTitle("Hapus Catatan")
            .setMessage("Apakah Anda yakin ingin menghapus catatan ini secara permanen?")
            .setPositiveButton("Hapus") { _, _ ->
                catatanViewModel.delete(catatan)
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val darkModeItem = menu?.findItem(R.id.action_dark_mode)
        darkModeItem?.title = if (ThemeManager.isDarkMode(this)) "Light Mode" else "Dark Mode"
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                showLogoutConfirmationDialog()
                true
            }
            R.id.action_dark_mode -> {
                val isCurrentlyDark = ThemeManager.isDarkMode(this)
                ThemeManager.setTheme(this, !isCurrentlyDark)
                // PERBAIKAN FINAL: Gunakan recreate() untuk menggambar ulang seluruh activity
                recreate()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(this)
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Logout") { _, _ ->
                handleLogout()
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun handleLogout() {
        Firebase.auth.signOut()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        GoogleSignIn.getClient(this, gso).signOut().addOnCompleteListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}

package com.example.uts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

// PERUBAHAN: Menggunakan model lokal 'Catatan'
class CatatanAdapter(
    private val onClick: (Catatan) -> Unit,
    private val onLongClick: (Catatan) -> Unit
) : ListAdapter<Catatan, CatatanAdapter.CatatanViewHolder>(CatatanDiffCallback) {

    class CatatanViewHolder(itemView: View, val onClick: (Catatan) -> Unit, val onLongClick: (Catatan) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val judulTextView: TextView = itemView.findViewById(R.id.textViewJudul)
        private val kontenTextView: TextView = itemView.findViewById(R.id.textViewKonten)
        private val tanggalTextView: TextView = itemView.findViewById(R.id.tvTanggal)
        private val pinImageView: ImageView = itemView.findViewById(R.id.imagePin)
        private var currentCatatan: Catatan? = null

        init {
            itemView.setOnClickListener {
                currentCatatan?.let {
                    onClick(it)
                }
            }
            itemView.setOnLongClickListener {
                currentCatatan?.let {
                    onLongClick(it)
                }
                true
            }
        }

        fun bind(catatan: Catatan) {
            currentCatatan = catatan
            judulTextView.text = catatan.judul
            kontenTextView.text = catatan.isi

            // PERUBAHAN: Pengecekan 'isPinned' sekarang menggunakan Boolean
            pinImageView.visibility = if (catatan.isPinned) View.VISIBLE else View.GONE

            tanggalTextView.text = formatTimestamp(catatan.tanggal)
        }

        private fun formatTimestamp(timestamp: Long): String {
            val date = Date(timestamp)
            val sdf = SimpleDateFormat("dd/MMM/yyyy HH:mm", Locale.forLanguageTag("id-ID"))
            sdf.timeZone = TimeZone.getTimeZone("GMT+7")
            return sdf.format(date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatatanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_catatan, parent, false)
        return CatatanViewHolder(view, onClick, onLongClick)
    }

    override fun onBindViewHolder(holder: CatatanViewHolder, position: Int) {
        val catatan = getItem(position)
        holder.bind(catatan)
    }
}

// PERUBAHAN: Menggunakan model lokal 'Catatan'
object CatatanDiffCallback : DiffUtil.ItemCallback<Catatan>() {
    override fun areItemsTheSame(oldItem: Catatan, newItem: Catatan): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Catatan, newItem: Catatan): Boolean {
        return oldItem == newItem
    }
}

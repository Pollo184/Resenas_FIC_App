package com.example.reseasfic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reseasfic.R
import android.util.Log

class ReseñaAdapter(private var reseñas: List<Reseña>) : RecyclerView.Adapter<ReseñaAdapter.ReseñaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReseñaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.resena_item, parent, false)
        return ReseñaViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReseñaViewHolder, position: Int) {

        val reseña = reseñas[position]
        Log.d("ReseñaAdapter", "Reseña en posición $position: $reseña")
        holder.tvFechaReseña.text = reseña.fechaReseña
        holder.tvContenidoReseña.text = reseña.contenidoReseña
    }

    override fun getItemCount(): Int {
        return reseñas.size
    }

    fun updateData(newReseñas: List<Reseña>) {
        reseñas = newReseñas
        notifyDataSetChanged()
    }

    inner class ReseñaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFechaReseña: TextView = itemView.findViewById(R.id.tvFechaReseña)
        val tvContenidoReseña: TextView = itemView.findViewById(R.id.tvContenidoReseña)
    }
}

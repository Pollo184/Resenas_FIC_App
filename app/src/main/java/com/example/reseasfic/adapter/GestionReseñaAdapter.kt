package com.example.reseasfic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reseasfic.R

class GestionReseñasAdapter(
    private val reseñas: List<Reseña>,
    private val eliminarReseñaCallback: (Reseña) -> Unit
) : RecyclerView.Adapter<GestionReseñasAdapter.GestionReseñaViewHolder>() {

    class GestionReseñaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewContenido: TextView = view.findViewById(R.id.textViewContenido)
        val textViewFecha: TextView = view.findViewById(R.id.textViewFecha)
        val buttonEliminar: Button = view.findViewById(R.id.buttonEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GestionReseñaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.resena_gestion_item, parent, false)
        return GestionReseñaViewHolder(view)
    }

    override fun onBindViewHolder(holder: GestionReseñaViewHolder, position: Int) {
        val reseña = reseñas[position]
        holder.textViewContenido.text = reseña.contenidoReseña
        holder.textViewFecha.text = reseña.fechaReseña
        holder.buttonEliminar.setOnClickListener {
            eliminarReseñaCallback(reseña)
        }
    }

    override fun getItemCount() = reseñas.size
}

package com.example.reseasfic.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reseasfic.R

class ProfesorAdapter(
    private val profesores: List<Profesor>,
    private val onClick: (Profesor) -> Unit
) : RecyclerView.Adapter<ProfesorAdapter.ProfesorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfesorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.profesor_item, parent, false)
        return ProfesorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfesorViewHolder, position: Int) {
        val profesor = profesores[position]
        holder.bind(profesor, onClick)
    }

    override fun getItemCount(): Int = profesores.size

    class ProfesorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profesorName: TextView = itemView.findViewById(R.id.profesor_name)

        fun bind(profesor: Profesor, onClick: (Profesor) -> Unit) {
            profesorName.text = "${profesor.nombre_docente} ${profesor.apellido_pat_docente} ${profesor.apellido_mat_docente}"
            itemView.setOnClickListener { onClick(profesor) }
        }
    }
}

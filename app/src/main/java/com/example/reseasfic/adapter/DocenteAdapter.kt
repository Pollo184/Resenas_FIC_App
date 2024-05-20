// DocenteAdapter.kt
package com.example.reseasfic.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reseasfic.R
import com.example.reseasfic.models.Docente

class DocenteAdapter(private val docentes: List<Docente>) :
    RecyclerView.Adapter<DocenteAdapter.DocenteViewHolder>() {

    class DocenteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreTextView: TextView = itemView.findViewById(R.id.tv_nombre)
        val correoTextView: TextView = itemView.findViewById(R.id.tv_correo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocenteViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.docente_item, parent, false)
        return DocenteViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DocenteViewHolder, position: Int) {
        val docente = docentes[position]
        holder.nombreTextView.text = "${docente.nombre_docente} ${docente.apellido_pat_docente} ${docente.apellido_mat_docente}"
        holder.correoTextView.text = docente.correo_docente
    }

    override fun getItemCount() = docentes.size
}

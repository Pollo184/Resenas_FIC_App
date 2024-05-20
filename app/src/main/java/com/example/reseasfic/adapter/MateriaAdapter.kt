package com.example.reseasfic.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.reseasfic.R

class MateriaAdapter(
    private val materias: List<Materia>,
    private val onClick: (Materia) -> Unit
) : RecyclerView.Adapter<MateriaAdapter.MateriaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MateriaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.materia_item, parent, false)
        return MateriaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MateriaViewHolder, position: Int) {
        val materia = materias[position]
        holder.bind(materia, onClick)
    }

    override fun getItemCount(): Int = materias.size

    class MateriaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val materiaName: TextView = itemView.findViewById(R.id.materia_name)

        fun bind(materia: Materia, onClick: (Materia) -> Unit) {
            materiaName.text = materia.nombre_materia
            itemView.setOnClickListener { onClick(materia) }
        }
    }
}

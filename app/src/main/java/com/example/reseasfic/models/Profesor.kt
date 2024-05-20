package com.example.reseasfic.models

data class Profesor(
    val id_docente: Int,
    val nombre_docente: String,
    val apellido_pat_docente: String,
    val apellido_mat_docente: String,
    val correo_docente: String
) {
    override fun toString(): String {
        return "$nombre_docente $apellido_pat_docente $apellido_mat_docente"
    }
}

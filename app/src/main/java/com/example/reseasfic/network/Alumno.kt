package com.example.reseasfic.network

data class Alumno(
    val id_alumno: Int,
    val nombre_alumno: String,
    val apellido_pat_alumno: String,
    val apellido_mat_alumno: String
) {
    override fun toString(): String {
        return "$nombre_alumno $apellido_pat_alumno $apellido_mat_alumno"
    }
}
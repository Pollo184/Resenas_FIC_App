package com.example.reseasfic.models

data class Materia(
    val id_materia: Int,
    val nombre_materia: String
) {
    override fun toString(): String {
        return nombre_materia
    }
}

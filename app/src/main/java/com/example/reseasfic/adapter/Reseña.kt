package com.example.reseasfic.adapter

import com.google.gson.annotations.SerializedName

data class Reseña(
    @SerializedName("id_reseña") val idReseña: String,
    @SerializedName("contenido_reseña") val contenidoReseña: String,
    @SerializedName("fecha_reseña") val fechaReseña: String
)

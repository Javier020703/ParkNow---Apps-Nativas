package com.example.parknow1.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Parqueadero(

    val id: String? = null,
    val nombre: String,
    val direccion: String,

    val descripcion: String? = null,

    val cupos_totales: Int,
    val cupos_disponibles: Int,
    val tarifa_hora: Double,


    val caracteristicas: List<String>? = emptyList(),

    val latitud: Double? = null,
    val longitud: Double? = null,

    val estado: String? = "activo",

    val creado_por: String? = null,
    val created_at: String? = null
)
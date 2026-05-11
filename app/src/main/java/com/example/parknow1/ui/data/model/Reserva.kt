package com.example.parknow1.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Reserva(

    val id: String? = null,

    val cliente: String,

    val parqueadero: String,

    val espacio: String,

    val hora: String,

    val estado: String = "activa",

    // SOLO UI
    var nombreCliente: String? = null,

    var nombreParqueadero: String? = null
)
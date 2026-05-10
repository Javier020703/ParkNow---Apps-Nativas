package com.example.parknow1.ui.admin_disabled.reservations

data class Reserva(
    val codigo: String,
    var cliente: String,
    var parqueadero: String,
    var espacio: String,
    var hora: String,
    var estado: String
)
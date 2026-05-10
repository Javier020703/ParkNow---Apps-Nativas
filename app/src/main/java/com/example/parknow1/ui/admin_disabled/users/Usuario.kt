package com.example.parknow1.ui.admin_disabled.users

data class Usuario(
    var nombre: String,
    var correo: String,
    var reservas: Int,
    var gasto: Double,
    var estado: String
)
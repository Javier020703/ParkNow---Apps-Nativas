package com.example.parknow1.ui.admin.users

data class Usuario(
    var nombre: String,
    var correo: String,
    var reservas: Int,
    var gasto: Double,
    var estado: String
)
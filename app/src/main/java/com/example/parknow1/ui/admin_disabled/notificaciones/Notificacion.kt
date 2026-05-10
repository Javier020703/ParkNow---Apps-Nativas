package com.example.parknow1.ui.admin_disabled.notificaciones

data class Notificacion(
    val titulo: String,
    val mensaje: String,
    val fecha: String,
    val tipo: String // success | warning | error
)
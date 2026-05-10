package com.example.parknow1.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(

    val id: String,

    val nombres: String,

    val apellidos: String,

    val correo: String? = null,

    val telefono: String? = null,

    val rol: String = "cliente",

    val foto_url: String? = null
)
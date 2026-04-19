package com.example.parknow1.ui.admin.users

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.parknow1.databinding.ActivityDetalleUsuarioBinding

class DetalleUsuarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetalleUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nombre = intent.getStringExtra("nombre") ?: ""
        val correo = intent.getStringExtra("correo") ?: ""

        binding.tvNombre.text = nombre
        binding.tvCorreo.text = correo
        binding.tvInfo.text = "Usuario activo en sistema"

        binding.btnEditar.setOnClickListener {
            val intent = Intent(this, FormUsuarioActivity::class.java)
            intent.putExtra("nombre", nombre)
            intent.putExtra("correo", correo)
            startActivity(intent)
        }
    }
}
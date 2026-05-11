package com.example.parknow1.ui.admin_disabled.users

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.parknow1.databinding.ActivityDetalleUsuarioBinding
import com.example.parknow1.data.repository.UserRepository
import kotlinx.coroutines.launch


class DetalleUsuarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetalleUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id") ?: return

        lifecycleScope.launch {

            val user = UserRepository.obtenerUsuarioPorId(id)

            user?.let {
                binding.tvNombre.text = it.nombres
                binding.tvCorreo.text = it.correo
                binding.tvInfo.text = it.rol
            }
        }
    }
}
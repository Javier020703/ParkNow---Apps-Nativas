package com.example.parknow1.ui.admin.reservations

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.parknow1.databinding.ActivityDetalleReservaBinding

class DetalleReservaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleReservaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleReservaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pos = intent.getIntExtra("pos", -1)

        if (pos != -1) {
            val reserva = ReservaProvider.lista[pos]

            binding.tvCodigo.text = reserva.codigo
            binding.tvCliente.text = reserva.cliente
            binding.tvEstado.text = reserva.estado
        }
    }
}
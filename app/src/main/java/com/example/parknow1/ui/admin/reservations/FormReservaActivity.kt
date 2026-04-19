package com.example.parknow1.ui.admin.reservations

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.parknow1.databinding.ActivityFormReservaBinding

class FormReservaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormReservaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormReservaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pos = intent.getIntExtra("pos", -1)

        if (pos != -1) {
            val reserva = ReservaProvider.lista[pos]
            binding.etCliente.setText(reserva.cliente)
        }

        binding.btnGuardar.setOnClickListener {

            val cliente = binding.etCliente.text.toString()

            if (pos != -1) {
                ReservaProvider.lista[pos].cliente = cliente
            } else {
                ReservaProvider.lista.add(
                    Reserva("PKN-" + (1000..9999).random(), cliente, "Centro", "A-1", "15:00", "Activa")
                )
            }

            finish()
        }
    }
}
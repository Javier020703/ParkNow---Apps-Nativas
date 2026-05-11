package com.example.parknow1.ui.admin_disabled.reservations

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.parknow1.databinding.ActivityDetalleReservaBinding

class DetalleReservaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleReservaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetalleReservaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // DATOS
        val reservaId =
            intent.getStringExtra("reserva_id") ?: ""

        val cliente =
            intent.getStringExtra("cliente") ?: ""

        val estado =
            intent.getStringExtra("estado") ?: ""

        val detalle =
            intent.getStringExtra("detalle") ?: ""

        // MOSTRAR
        binding.tvCodigo.text =
            "Reserva #${reservaId.take(8)}"

        binding.tvCliente.text =
            cliente

        binding.tvEstado.text =
            estado.replaceFirstChar { it.uppercase() }

        binding.tvDetalle.text =
            detalle

        // BOTON EDITAR
        binding.btnEditar.setOnClickListener {

            val intent = Intent(
                this,
                FormReservaActivity::class.java
            )

            intent.putExtra(
                "reserva_id",
                reservaId
            )

            startActivity(intent)
        }
    }
}
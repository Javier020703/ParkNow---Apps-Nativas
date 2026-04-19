package com.example.parknow1.ui.admin.tarifas

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.parknow1.R

class FormTarifaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_tarifa)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etPrecio = findViewById<EditText>(R.id.etPrecio)
        val etDescripcion = findViewById<EditText>(R.id.etDescripcion)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        btnGuardar.setOnClickListener {

            val nombre = etNombre.text.toString()
            val precio = etPrecio.text.toString()
            val descripcion = etDescripcion.text.toString()

            val nueva = Tarifa(
                TarifaRepository.listaTarifas.size + 1,
                nombre,
                precio,
                descripcion
            )

            TarifaRepository.agregar(nueva)

            Toast.makeText(this, "Tarifa guardada", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
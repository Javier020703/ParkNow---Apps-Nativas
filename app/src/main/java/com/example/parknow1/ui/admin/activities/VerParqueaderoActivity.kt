package com.example.parknow1.ui.admin.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.parknow1.R
import com.example.parknow1.ui.admin.activities.FormParqueaderoActivity

class VerParqueaderoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_parqueadero)

        val nombre = findViewById<TextView>(R.id.tvNombre)
        val direccion = findViewById<TextView>(R.id.tvDireccion)
        val espacios = findViewById<TextView>(R.id.tvEspacios)
        val tarifa = findViewById<TextView>(R.id.tvTarifa)

        // simular datos
        nombre.text = "Parqueadero Centro"
        direccion.text = "Calle 45 #23-10"
        espacios.text = "Espacios: 30"
        tarifa.text = "Tarifa: $5/h"

        findViewById<Button>(R.id.btnEditar).setOnClickListener {
            startActivity(Intent(this, FormParqueaderoActivity::class.java))
        }
    }
}
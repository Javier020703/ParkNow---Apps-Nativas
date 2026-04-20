package com.example.parknow1.ui.admin.users

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parknow1.R

class FormUsuarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_form_usuario)

        // REFERENCIAS A LOS ELEMENTOS
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        // BOTÓN GUARDAR
        btnGuardar.setOnClickListener {

            val nombre = etNombre.text.toString().trim()
            val correo = etCorreo.text.toString().trim()

            if (nombre.isEmpty() || correo.isEmpty()) {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //  luego conectamos crud real
            Toast.makeText(this, "Usuario guardado", Toast.LENGTH_SHORT).show()

            // Limpiar campos
            etNombre.setText("")
            etCorreo.setText("")
        }
    }
}
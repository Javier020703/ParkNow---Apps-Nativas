package com.example.parknow1.ui.admin_disabled.users

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.parknow1.R
import com.example.parknow1.data.model.User
import com.example.parknow1.data.repository.UserRepository
import kotlinx.coroutines.launch
import java.util.UUID

class FormUsuarioActivity : AppCompatActivity() {

    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_usuario)

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etCorreo = findViewById<EditText>(R.id.etCorreo)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)

        userId = intent.getStringExtra("id")

        if (userId != null) {
            cargarUsuario(etNombre, etCorreo)
        }

        btnGuardar.setOnClickListener {

            val nombre = etNombre.text.toString()
            val correo = etCorreo.text.toString()

            lifecycleScope.launch {

                if (userId == null) {

                    UserRepository.insertarUsuario(
                        User(
                            id = UUID.randomUUID().toString(),
                            nombres = nombre,
                            apellidos = "",
                            correo = correo,
                            telefono = "",
                            rol = "cliente"
                        )
                    )

                } else {

                    UserRepository.actualizarUsuario(
                        User(
                            id = userId!!,
                            nombres = nombre,
                            apellidos = "",
                            correo = correo,
                            telefono = "",
                            rol = "cliente"
                        )
                    )
                }

                finish()
            }
        }
    }

    private fun cargarUsuario(etNombre: EditText, etCorreo: EditText) {

        lifecycleScope.launch {

            val user = UserRepository.obtenerUsuarioPorId(userId!!)

            user?.let {
                etNombre.setText(it.nombres)
                etCorreo.setText(it.correo)
            }
        }
    }
}
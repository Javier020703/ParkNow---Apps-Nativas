package com.example.parknow1.ui.auth

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.parknow1.R
import android.content.Intent
import android.os.Handler
import android.os.Looper

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val name = findViewById<EditText>(R.id.etName)
        val email = findViewById<EditText>(R.id.etEmail)
        val phone = findViewById<EditText>(R.id.etPhone)
        val password = findViewById<EditText>(R.id.etPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {

            if (name.text.toString().isEmpty()) {
                name.error = "Ingrese nombre"
                return@setOnClickListener
            }

            if (email.text.toString().isEmpty()) {
                email.error = "Ingrese correo"
                return@setOnClickListener
            }

            if (phone.text.toString().isEmpty()) {
                phone.error = "Ingrese teléfono"
                return@setOnClickListener
            }

            if (password.text.toString().isEmpty()) {
                password.error = "Ingrese contraseña"
                return@setOnClickListener
            }

            Toast.makeText(this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }, 2000)
        }
    }
}
package com.example.parknow1.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.parknow1.R
import com.example.parknow1.ui.admin.AdminActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById<EditText>(R.id.etEmail)
        val password = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val txtRegister = findViewById<TextView>(R.id.txtRegister)

        // BOTÓN LOGIN
        btnLogin.setOnClickListener {

            val emailText = email.text.toString()
            val passText = password.text.toString()

            if (emailText.isEmpty()) {
                email.error = "Ingrese su correo"
                return@setOnClickListener
            }

            if (passText.isEmpty()) {
                password.error = "Ingrese su contraseña"
                return@setOnClickListener
            }

            // 🔥 LOGIN SIMULADO
            Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, AdminActivity::class.java))
            finish()
        }

        // IR A REGISTRO
        txtRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
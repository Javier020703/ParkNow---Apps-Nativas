package com.example.parknow1.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.*

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope

import com.example.parknow1.R
import com.example.parknow1.data.remote.SupabaseClient
import com.example.parknow1.data.repository.UserRepository

import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email

import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class RegisterActivity : AppCompatActivity() {

    private lateinit var etNombres: EditText
    private lateinit var etApellidos: EditText
    private lateinit var etEmail: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText

    private lateinit var checkTerminos: CheckBox

    private lateinit var btnRegister: Button
    private lateinit var txtIrLogin: TextView

    private val client = SupabaseClient.client

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        etNombres = findViewById(R.id.etNombres)
        etApellidos = findViewById(R.id.etApellidos)
        etEmail = findViewById(R.id.etEmail)
        etTelefono = findViewById(R.id.etTelefono)

        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)

        checkTerminos = findViewById(R.id.checkTerminos)

        btnRegister = findViewById(R.id.btnRegister)
        txtIrLogin = findViewById(R.id.txtIrLogin)

        btnRegister.setOnClickListener {

            registrarUsuario()
        }

        txtIrLogin.setOnClickListener {

            startActivity(
                Intent(this, LoginActivity::class.java)
            )

            finish()
        }
    }

    private fun registrarUsuario() {

        val nombres = etNombres.text.toString().trim()
        val apellidos = etApellidos.text.toString().trim()

        val correo = etEmail.text.toString().trim()
        val telefono = etTelefono.text.toString().trim()

        val password = etPassword.text.toString().trim()
        val confirmPassword =
            etConfirmPassword.text.toString().trim()

        // VALIDAR CAMPOS
        if (
            nombres.isEmpty() ||
            apellidos.isEmpty() ||
            correo.isEmpty() ||
            telefono.isEmpty() ||
            password.isEmpty() ||
            confirmPassword.isEmpty()
        ) {

            Toast.makeText(
                this,
                "Completa todos los campos",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        // VALIDAR PASSWORD
        if (password != confirmPassword) {

            Toast.makeText(
                this,
                "Las contraseñas no coinciden",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        // VALIDAR TERMINOS
        if (!checkTerminos.isChecked) {

            Toast.makeText(
                this,
                "Debes aceptar los términos",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        lifecycleScope.launch {

            try {

                // REGISTRO AUTH
                client.auth.signUpWith(Email) {

                    email = correo
                    this.password = password

                    data = buildJsonObject {

                        put("nombres", nombres)
                        put("apellidos", apellidos)
                    }
                }

                // OBTENER ID
                val userId =
                    client.auth.currentUserOrNull()?.id ?: ""

                // INSERTAR EN TABLA usuarios
                UserRepository.insertarUsuario(
                    id = userId,
                    nombres = nombres,
                    apellidos = apellidos,
                    correo = correo,
                    telefono = telefono,
                    rol = "cliente"
                )

                runOnUiThread {

                    Toast.makeText(
                        this@RegisterActivity,
                        "Registro exitoso",
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(
                        Intent(
                            this@RegisterActivity,
                            LoginActivity::class.java
                        )
                    )

                    finish()
                }

            } catch (e: Exception) {

                runOnUiThread {

                    Toast.makeText(
                        this@RegisterActivity,
                        "Error: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}
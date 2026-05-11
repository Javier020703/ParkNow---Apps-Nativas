package com.example.parknow1.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.lifecycleScope
import com.example.parknow1.R
import com.example.parknow1.ui.MainActivity
import com.example.parknow1.data.remote.SupabaseClient
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.Google
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.providers.builtin.IDToken
import kotlinx.coroutines.launch
import com.example.parknow1.data.repository.UserRepository
import com.example.parknow1.ui.admin_disabled.AdminActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnGoogle: LinearLayout
    private lateinit var txtRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inputs
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)

        // Botones
        btnLogin = findViewById(R.id.btnLogin)
        btnGoogle = findViewById(R.id.btnGoogleLogin)

        // Textos
        txtRegister = findViewById(R.id.txtRegister)

        // Login email
        btnLogin.setOnClickListener {
            loginEmail()
        }

        // Login Google
        btnGoogle.setOnClickListener {
            iniciarSesionConGoogle()
        }

        // Ir a Register
        txtRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    // ---------------- LOGIN EMAIL ----------------

    private fun loginEmail() {

        val correo = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (correo.isEmpty() || password.isEmpty()) {

            Toast.makeText(
                this,
                "Completa todos los campos",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        lifecycleScope.launch {

            try {

                SupabaseClient.client.auth.signInWith(Email) {

                    email = correo
                    this.password = password
                }

                Toast.makeText(
                    this@LoginActivity,
                    "Inicio de sesión exitoso",
                    Toast.LENGTH_SHORT
                ).show()

                verificarRolYRedirigir()

            } catch (e: Exception) {

                Toast.makeText(
                    this@LoginActivity,
                    "Error: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    // ---------------- LOGIN GOOGLE ----------------

    private fun iniciarSesionConGoogle() {

        lifecycleScope.launch {

            try {

                val googleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId("140427932406-94lt4tmv8qlqtl221o4ipmmd7jodluiq.apps.googleusercontent.com")
                    .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val credentialManager =
                    CredentialManager.create(this@LoginActivity)

                val result = credentialManager.getCredential(
                    this@LoginActivity,
                    request
                )

                val credential = GoogleIdTokenCredential.createFrom(
                    result.credential.data
                )

                // LOGIN SUPABASE
                SupabaseClient.client.auth.signInWith(IDToken) {

                    idToken = credential.idToken
                    provider = Google
                }

                // OBTENER USUARIO
                val user =
                    SupabaseClient.client.auth.currentUserOrNull()

                if (user != null) {

                    // VALIDAR SI YA EXISTE
                    val existe =
                        UserRepository.existeUsuario(user.id)

                    if (!existe) {

                        val nombreCompleto =
                            user.userMetadata
                                ?.get("full_name")
                                ?.toString()
                                ?.replace("\"", "")
                                ?: ""

                        val partes =
                            nombreCompleto.split(" ")

                        val nombres =
                            partes.firstOrNull() ?: ""

                        val apellidos =
                            partes.drop(1)
                                .joinToString(" ")

                        val correoGoogle =
                            user.email ?: ""

                        UserRepository.insertarUsuario(
                            id = user.id,
                            nombres = nombres,
                            apellidos = apellidos,
                            correo = correoGoogle,
                            telefono = ""
                        )
                    }
                }

                Toast.makeText(
                    this@LoginActivity,
                    "Google Login exitoso",
                    Toast.LENGTH_SHORT
                ).show()

                verificarRolYRedirigir()

            } catch (e: Exception) {

                Toast.makeText(
                    this@LoginActivity,
                    "Error Google: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    // ---- Funcion de revision de rol ---
    private fun verificarRolYRedirigir() {

        lifecycleScope.launch {

            try {

                val rol =
                    UserRepository.obtenerRolActual()

                if (rol == "admin") {

                    startActivity(
                        Intent(
                            this@LoginActivity,
                            AdminActivity::class.java
                        )
                    )

                } else {

                    startActivity(
                        Intent(
                            this@LoginActivity,
                            MainActivity::class.java
                        )
                    )
                }

                finish()

            } catch (e: Exception) {

                Toast.makeText(
                    this@LoginActivity,
                    "Error obteniendo rol",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

}
package com.example.parknow1.ui.admin_disabled.reservations

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.parknow1.data.model.Parqueadero
import com.example.parknow1.data.model.Reserva
import com.example.parknow1.data.model.User
import com.example.parknow1.data.repository.ParqueaderoRepository
import com.example.parknow1.data.repository.ReservaRepository
import com.example.parknow1.data.repository.UserRepository
import com.example.parknow1.databinding.ActivityFormReservaBinding
import kotlinx.coroutines.launch

class FormReservaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormReservaBinding

    // =========================
    // EDITAR
    // =========================

    private var reservaId: String? = null

    // =========================
    // LISTAS
    // =========================

    private var listaUsuarios = listOf<User>()

    private var listaParqueaderos =
        listOf<Parqueadero>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding =
            ActivityFormReservaBinding.inflate(
                layoutInflater
            )

        setContentView(binding.root)

        // =========================
        // RECIBIR ID
        // =========================

        reservaId =
            intent.getStringExtra("reserva_id")

        // =========================
        // CARGAR DATOS
        // =========================

        cargarUsuarios()

        cargarParqueaderos()

        // =========================
        // GUARDAR
        // =========================

        binding.btnGuardar.setOnClickListener {

            guardarReserva()
        }
    }

    // =====================================================
    // CARGAR USUARIOS
    // =====================================================

    private fun cargarUsuarios() {

        lifecycleScope.launch {

            try {

                listaUsuarios =
                    UserRepository.obtenerUsuarios()

                val nombres =
                    listaUsuarios.map {

                        "${it.nombres} ${it.apellidos}"
                    }

                val adapter =
                    ArrayAdapter(

                        this@FormReservaActivity,

                        android.R.layout.simple_spinner_item,

                        nombres
                    )

                adapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item
                )

                binding.spUsuarios.adapter =
                    adapter

                cargarReservaSiEditando()

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

    // =====================================================
    // CARGAR PARQUEADEROS
    // =====================================================

    private fun cargarParqueaderos() {

        lifecycleScope.launch {

            try {

                listaParqueaderos =
                    ParqueaderoRepository.obtenerParqueaderos()

                val nombres =
                    listaParqueaderos.map {
                        it.nombre
                    }

                val adapter =
                    ArrayAdapter(

                        this@FormReservaActivity,

                        android.R.layout.simple_spinner_item,

                        nombres
                    )

                adapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item
                )

                binding.spParqueaderos.adapter =
                    adapter

                cargarReservaSiEditando()

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

    // =====================================================
    // CARGAR RESERVA (EDITAR)
    // =====================================================

    private fun cargarReservaSiEditando() {

        if (
            reservaId == null ||
            listaUsuarios.isEmpty() ||
            listaParqueaderos.isEmpty()
        ) {
            return
        }

        lifecycleScope.launch {

            try {

                val reserva =
                    ReservaRepository.obtenerReserva(
                        reservaId!!
                    )

                reserva?.let {

                    binding.etEspacio.setText(
                        it.espacio
                    )

                    binding.etHora.setText(
                        it.hora
                    )

                    // =========================
                    // SELECCIONAR USUARIO
                    // =========================

                    val indexUsuario =
                        listaUsuarios.indexOfFirst { u ->

                            u.id == it.cliente
                        }

                    if (indexUsuario >= 0) {

                        binding.spUsuarios
                            .setSelection(indexUsuario)
                    }

                    // =========================
                    // SELECCIONAR PARQUEADERO
                    // =========================

                    val indexParqueadero =
                        listaParqueaderos.indexOfFirst { p ->

                            p.id == it.parqueadero
                        }

                    if (indexParqueadero >= 0) {

                        binding.spParqueaderos
                            .setSelection(indexParqueadero)
                    }
                }

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

    // =====================================================
    // GUARDAR
    // =====================================================

    private fun guardarReserva() {

        val espacio =
            binding.etEspacio.text.toString().trim()

        val hora =
            binding.etHora.text.toString().trim()

        // =========================
        // VALIDACIONES
        // =========================

        if (
            espacio.isEmpty() ||
            hora.isEmpty()
        ) {

            Toast.makeText(

                this,

                "Completa todos los campos",

                Toast.LENGTH_SHORT

            ).show()

            return
        }

        val usuarioSeleccionado =
            listaUsuarios[
                binding.spUsuarios.selectedItemPosition
            ]

        val parqueaderoSeleccionado =
            listaParqueaderos[
                binding.spParqueaderos.selectedItemPosition
            ]

        val reserva = Reserva(

            id = reservaId,

            cliente =
                usuarioSeleccionado.id,

            parqueadero =
                parqueaderoSeleccionado.id!!,

            espacio = espacio,

            hora = hora,

            estado = "activa"
        )

        lifecycleScope.launch {

            try {

                // =========================
                // CREAR O EDITAR
                // =========================

                if (reservaId == null) {

                    ReservaRepository.insertReserva(
                        reserva
                    )

                } else {

                    ReservaRepository.updateReserva(
                        reserva
                    )
                }

                Toast.makeText(

                    this@FormReservaActivity,

                    "Reserva guardada",

                    Toast.LENGTH_SHORT

                ).show()

                finish()

            } catch (e: Exception) {

                e.printStackTrace()

                Toast.makeText(

                    this@FormReservaActivity,

                    "Error: ${e.message}",

                    Toast.LENGTH_LONG

                ).show()
            }
        }
    }
}
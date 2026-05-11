package com.example.parknow1.ui.reservation

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.parknow1.R
import com.example.parknow1.data.model.Reserva
import com.example.parknow1.data.repository.ReservaRepository
import com.example.parknow1.data.repository.UserRepository
import kotlinx.coroutines.launch
import java.util.*

class ReservationFragment : Fragment() {

    private lateinit var txtParkingName: TextView
    private lateinit var etFechaHora: EditText
    private lateinit var spinnerDuracion: Spinner
    private lateinit var txtSubtotal: TextView
    private lateinit var btnContinuar: Button

    private var fechaHoraSeleccionada = ""
    private var tarifaHora = 4000.0
    private var horasSeleccionadas = 1

    private var parqueaderoId = ""
    private var parqueaderoNombre = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_reservation,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        txtParkingName = view.findViewById(R.id.txtParkingName)
        etFechaHora = view.findViewById(R.id.etFechaHora)
        spinnerDuracion = view.findViewById(R.id.spinnerDuracion)
        txtSubtotal = view.findViewById(R.id.txtSubtotal)
        btnContinuar = view.findViewById(R.id.btnContinuar)

        // =========================
        // DATOS RECIBIDOS
        // =========================

        parqueaderoId = arguments?.getString("parqueadero_id") ?: ""
        parqueaderoNombre = arguments?.getString("parqueadero_nombre") ?: ""
        tarifaHora = arguments?.getDouble("tarifa") ?: 4000.0

        txtParkingName.text = parqueaderoNombre

        // =========================
        // SPINNER DURACIÓN
        // =========================

        val items = listOf("1 hora", "2 horas", "3 horas", "4 horas")

        spinnerDuracion.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            items
        )

        spinnerDuracion.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    horasSeleccionadas = position + 1
                    calcularSubtotal()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

        // =========================
        // FECHA Y HORA
        // =========================

        etFechaHora.setOnClickListener {
            mostrarFechaHora()
        }

        // =========================
        // CONTINUAR
        // =========================

        btnContinuar.setOnClickListener {
            crearReserva()
        }
    }

    // =====================================================
    // CALCULAR TOTAL
    // =====================================================

    private fun calcularSubtotal() {
        val total = horasSeleccionadas * tarifaHora
        txtSubtotal.text = "$$total"
    }

    // =====================================================
    // SELECTOR FECHA HORA
    // =====================================================

    private fun mostrarFechaHora() {

        val calendario = Calendar.getInstance()

        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->

                TimePickerDialog(
                    requireContext(),
                    { _, hour, minute ->

                        fechaHoraSeleccionada =
                            "$day/${month + 1}/$year $hour:$minute"

                        etFechaHora.setText(fechaHoraSeleccionada)

                    },
                    calendario.get(Calendar.HOUR_OF_DAY),
                    calendario.get(Calendar.MINUTE),
                    true
                ).show()

            },
            calendario.get(Calendar.YEAR),
            calendario.get(Calendar.MONTH),
            calendario.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    // =====================================================
    // CREAR RESERVA
    // =====================================================

    private fun crearReserva() {

        if (
            parqueaderoId.isBlank() ||
            fechaHoraSeleccionada.isBlank()
        ) {
            Toast.makeText(
                requireContext(),
                "Completa todos los datos",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        lifecycleScope.launch {

            try {

                val usuario = UserRepository.obtenerUsuario()

                if (usuario == null) {

                    Toast.makeText(
                        requireContext(),
                        "Usuario no encontrado",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@launch
                }

                val reserva = Reserva(
                    cliente = usuario.id ?: "",
                    parqueadero = parqueaderoId,
                    espacio = "A1",
                    hora = fechaHoraSeleccionada,
                    estado = "activa"
                )

                ReservaRepository.insertReserva(reserva)

                Toast.makeText(
                    requireContext(),
                    "Reserva creada correctamente",
                    Toast.LENGTH_SHORT
                ).show()

                // =========================
                // IR A ACTIVE RESERVATION
                // =========================

                val fragment = ActiveReservationFragment()

                val bundle = Bundle().apply {
                    putString("parqueadero_nombre", parqueaderoNombre)
                    putString("espacio", reserva.espacio)
                    putString("hora", fechaHoraSeleccionada)
                    putString("codigo", "PKN-${(1000..9999).random()}")
                }

                fragment.arguments = bundle

                parentFragmentManager.beginTransaction()
                    .replace(R.id.contenedor, fragment)
                    .addToBackStack(null)
                    .commit()

            } catch (e: Exception) {

                e.printStackTrace()

                Toast.makeText(
                    requireContext(),
                    "Error creando reserva: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
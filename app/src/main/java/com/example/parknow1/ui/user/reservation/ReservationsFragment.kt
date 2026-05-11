package com.example.parknow1.ui.reservation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.parknow1.R
import com.example.parknow1.data.repository.ReservaRepository
import com.example.parknow1.data.repository.UserRepository
import com.example.parknow1.data.repository.ParqueaderoRepository

import kotlinx.coroutines.launch

class ReservationsFragment : Fragment(R.layout.fragment_reservations) {

    private lateinit var recycler: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        recycler = view.findViewById(R.id.recyclerReservations)

        recycler.layoutManager =
            LinearLayoutManager(requireContext())

        cargarReservas()
    }

    private fun cargarReservas() {

        lifecycleScope.launch {

            try {

                // 1. Usuario actual
                val usuario = UserRepository.obtenerUsuario()

                if (usuario == null) {

                    Toast.makeText(
                        requireContext(),
                        "Usuario no encontrado",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@launch
                }

                // 2. Traer datos
                val reservas = ReservaRepository.obtenerReservas()
                val parques = ParqueaderoRepository.obtenerParqueaderos()

                // 3. Filtrar reservas del usuario
                val mias = reservas.filter {
                    it.cliente == usuario.id
                }

                // 4. REEMPLAZAR ID POR NOMBRE DEL PARQUEADERO
                val mapeadas = mias.map { reserva ->

                    val parque = parques.find {
                        it.id == reserva.parqueadero
                    }

                    reserva.copy(
                        parqueadero = parque?.nombre ?: "Desconocido"
                    )
                }

                // 5. Adapter
                recycler.adapter = ReservationAdapter(mapeadas)

            } catch (e: Exception) {

                e.printStackTrace()

                Toast.makeText(
                    requireContext(),
                    "Error cargando reservas: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
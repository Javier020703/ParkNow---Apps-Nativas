package com.example.parknow1.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

import com.example.parknow1.R
import com.example.parknow1.data.repository.UserRepository
import com.example.parknow1.ui.user.map.ParkingDetailFragment
import com.google.android.material.card.MaterialCardView

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private lateinit var tvSaludo: TextView

    private lateinit var cardParking1: MaterialCardView
    private lateinit var cardParking2: MaterialCardView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(
            R.layout.fragment_home,
            container,
            false
        )

        // SALUDO
        tvSaludo = view.findViewById(R.id.tvSaludo)

        // CARDS
        cardParking1 = view.findViewById(R.id.cardParking1)
        cardParking2 = view.findViewById(R.id.cardParking2)

        cargarUsuario()

        // CLICK CARD 1
        cardParking1.setOnClickListener {

            abrirDetalleParqueadero()
        }

        // CLICK CARD 2
        cardParking2.setOnClickListener {

            abrirDetalleParqueadero()
        }

        return view
    }

    // ---------------- USUARIO ----------------

    private fun cargarUsuario() {

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val usuario = UserRepository.obtenerUsuario()

                withContext(Dispatchers.Main) {

                    val nombre = usuario?.nombres ?: "Usuario"

                    tvSaludo.text = "¡Hola, $nombre!"
                }

            } catch (e: Exception) {

                withContext(Dispatchers.Main) {

                    tvSaludo.text = "¡Hola!"
                }
            }
        }
    }

    // ---------------- DETALLE ----------------

    private fun abrirDetalleParqueadero() {

        parentFragmentManager.beginTransaction()
            .replace(
                R.id.contenedor,
                ParkingDetailFragment()
            )
            .addToBackStack(null)
            .commit()
    }
}
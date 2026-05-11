package com.example.parknow1.ui.reservation

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.parknow1.R

class ActiveReservationFragment : Fragment(R.layout.fragment_active_reservation) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val txtCodigo = view.findViewById<TextView>(R.id.txtCodigoReserva)
        val txtParking = view.findViewById<TextView>(R.id.txtParking)
        val txtEspacio = view.findViewById<TextView>(R.id.txtEspacio)
        val txtEntrada = view.findViewById<TextView>(R.id.txtEntrada)

        val nombre = arguments?.getString("parqueadero_nombre") ?: ""
        val espacio = arguments?.getString("espacio") ?: ""
        val hora = arguments?.getString("hora") ?: ""
        val codigo = arguments?.getString("codigo") ?: ""

        txtCodigo.text = codigo
        txtParking.text = nombre
        txtEspacio.text = espacio
        txtEntrada.text = hora
    }
}
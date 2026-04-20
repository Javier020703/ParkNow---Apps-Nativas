package com.example.parknow1.ui.admin.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.parknow1.R
import com.example.parknow1.ui.admin.adapters.ParqueaderoAdapter
import com.example.parknow1.ui.admin.models.Parqueadero

class AgregarParqueaderoFragment(
    private val lista: MutableList<Parqueadero>,
    private val adapter: ParqueaderoAdapter
) : Fragment(R.layout.fragment_agregar_parqueadero) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val nombre = view.findViewById<EditText>(R.id.etNombre)
        val direccion = view.findViewById<EditText>(R.id.etDireccion)
        val cupos = view.findViewById<EditText>(R.id.etCupos)
        val tarifa = view.findViewById<EditText>(R.id.etTarifa)
        val btnGuardar = view.findViewById<Button>(R.id.btnGuardar)

        btnGuardar.setOnClickListener {

            val nuevo = Parqueadero(
                nombre.text.toString(),
                direccion.text.toString(),
                cupos.text.toString().toIntOrNull() ?: 0,
                tarifa.text.toString()
            )

            lista.add(nuevo)
            adapter.notifyDataSetChanged()

            parentFragmentManager.popBackStack()
        }
    }
}
package com.example.parknow1.ui.admin.roles

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parknow1.R

class RolesFragment : Fragment() {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: AdapterRoles
    private lateinit var btnAgregar: Button
    private lateinit var etNombre: EditText
    private lateinit var etPermisos: EditText

    private val lista = mutableListOf<Rol>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_roles, container, false)

        recycler = view.findViewById(R.id.recyclerRoles)
        btnAgregar = view.findViewById(R.id.btnAgregarRol)
        etNombre = view.findViewById(R.id.etNombreRol)
        etPermisos = view.findViewById(R.id.etPermisosRol)

        adapter = AdapterRoles(lista)
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        cargarDatos()

        btnAgregar.setOnClickListener {

            val nombre = etNombre.text.toString()

            val permisos = etPermisos.text.toString()
                .split(",")
                .map { it.trim() }

            if (nombre.isNotEmpty()) {

                lista.add(Rol(nombre, permisos))
                adapter.notifyDataSetChanged()

                etNombre.setText("")
                etPermisos.setText("")
            }
        }

        return view
    }

    private fun cargarDatos() {

        lista.add(Rol("Administrador", listOf("CRUD", "Usuarios", "Reportes")))
        lista.add(Rol("Operador", listOf("Ver", "Editar")))
        lista.add(Rol("Cliente", listOf("Reservar", "Pagar")))
    }
}
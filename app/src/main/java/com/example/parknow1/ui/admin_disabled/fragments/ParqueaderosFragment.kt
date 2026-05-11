package com.example.parknow1.ui.admin_disabled.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.parknow1.R
import com.example.parknow1.data.model.Parqueadero
import com.example.parknow1.data.repository.ParqueaderoRepository
import com.example.parknow1.ui.admin_disabled.activities.FormParqueaderoActivity
import com.example.parknow1.ui.admin_disabled.adapters.ParqueaderoAdapter

import com.google.android.material.floatingactionbutton.FloatingActionButton

import kotlinx.coroutines.launch

class ParqueaderosFragment :
    Fragment(R.layout.fragment_parqueaderos) {

    private lateinit var recycler: RecyclerView

    private lateinit var adapter: ParqueaderoAdapter

    private val lista = mutableListOf<Parqueadero>()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(view, savedInstanceState)

        recycler =
            view.findViewById(R.id.recyclerParqueaderos)

        val fab =
            view.findViewById<FloatingActionButton>(
                R.id.fabAgregar
            )

        // Recycler
        recycler.layoutManager =
            LinearLayoutManager(requireContext())

        adapter =
            ParqueaderoAdapter(
                lista
            ) { parqueadero  ->

                eliminarParqueadero(parqueadero.id)
            }

        recycler.adapter = adapter

        // Cargar datos
        cargarParqueaderos()

        // Agregar
        fab.setOnClickListener {

            startActivity(
                Intent(
                    requireContext(),
                    FormParqueaderoActivity::class.java
                )
            )
        }
    }

    override fun onResume() {
        super.onResume()
        cargarParqueaderos()
    }

    private fun cargarParqueaderos() {

        viewLifecycleOwner.lifecycleScope.launch {

            try {

                val datos =
                    ParqueaderoRepository
                        .obtenerParqueaderos()

                Log.d(
                    "PARQUEADEROS",
                    datos.toString()
                )

                lista.clear()

                lista.addAll(datos)

                adapter.notifyDataSetChanged()

            } catch (e: Exception) {

                Log.e(
                    "PARQUEADEROS",
                    "Error: ${e.message}"
                )
            }
        }
    }

    private fun eliminarParqueadero(id: String?) {

        if (id == null) return

        viewLifecycleOwner.lifecycleScope.launch {

            try {

                ParqueaderoRepository
                    .eliminarParqueadero(id)

                cargarParqueaderos()

            } catch (e: Exception) {

                Log.e(
                    "DELETE",
                    "Error: ${e.message}"
                )
            }
        }
    }
}
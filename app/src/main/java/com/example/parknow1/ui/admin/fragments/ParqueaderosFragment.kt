package com.example.parknow1.ui.admin.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parknow1.R
import com.example.parknow1.ui.admin.activities.FormParqueaderoActivity
import com.example.parknow1.ui.admin.adapters.ParqueaderoAdapter
import com.example.parknow1.ui.admin.models.Parqueadero
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ParqueaderosFragment : Fragment(R.layout.fragment_parqueaderos) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerParqueaderos)
        val fab = view.findViewById<FloatingActionButton>(R.id.fabAgregar)

        val lista = mutableListOf(
            Parqueadero("Centro", "Calle 45", 30, "$5/h"),
            Parqueadero("Norte", "Av 10", 50, "$4/h"),
            Parqueadero("Sur", "Zona Sur", 20, "$3/h")
        )

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = ParqueaderoAdapter(lista)

        fab.setOnClickListener {
            startActivity(Intent(requireContext(), FormParqueaderoActivity::class.java))
        }
    }
}
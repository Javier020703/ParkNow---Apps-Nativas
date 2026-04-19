package com.example.parknow1.ui.admin.tarifas

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parknow1.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TarifasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_tarifas, container, false)

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerTarifas)
        val fab = view.findViewById<FloatingActionButton>(R.id.fabAgregar)

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = TarifaAdapter(TarifaRepository.listaTarifas)

        fab.setOnClickListener {
            startActivity(Intent(requireContext(), FormTarifaActivity::class.java))
        }

        return view
    }
}
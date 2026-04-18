package com.example.parknow1.ui.admin.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parknow1.R
import com.example.parknow1.ui.admin.adapters.ParqueaderoAdapter

class ParqueaderosFragment : Fragment(R.layout.fragment_parqueaderos) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerParqueaderos)

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = ParqueaderoAdapter()
    }
}
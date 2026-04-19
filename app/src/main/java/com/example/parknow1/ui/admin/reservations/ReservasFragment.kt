package com.example.parknow1.ui.admin.reservations

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parknow1.databinding.FragmentReservasBinding

class ReservasFragment : Fragment() {

    private lateinit var binding: FragmentReservasBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentReservasBinding.inflate(inflater, container, false)

        val adapter = ReservaAdapter(ReservaProvider.lista)

        binding.recyclerReservas.layoutManager = LinearLayoutManager(context)
        binding.recyclerReservas.adapter = adapter

        binding.fabAgregar.setOnClickListener {
            startActivity(Intent(requireContext(), FormReservaActivity::class.java))
        }

        return binding.root
    }
}
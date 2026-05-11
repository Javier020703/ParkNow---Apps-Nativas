package com.example.parknow1.ui.admin_disabled.reservations

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parknow1.R
import com.example.parknow1.databinding.FragmentReservasBinding

class ReservasFragment : Fragment(R.layout.fragment_reservas) {

    private lateinit var binding: FragmentReservasBinding
    private lateinit var viewModel: ReservaViewModel
    private lateinit var adapter: ReservaAdapter

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

        super.onViewCreated(view, savedInstanceState)

        binding = FragmentReservasBinding.bind(view)

        viewModel =
            ViewModelProvider(this)[ReservaViewModel::class.java]

        adapter = ReservaAdapter(

            emptyList(),

            onVer = { reserva ->

                val intent = Intent(
                    requireContext(),
                    DetalleReservaActivity::class.java
                )

                intent.putExtra("reserva_id", reserva.id)
                intent.putExtra("cliente", reserva.cliente)
                intent.putExtra("estado", reserva.estado)

                intent.putExtra(
                    "detalle",
                    "Espacio ${reserva.espacio} • ${reserva.hora}"
                )

                startActivity(intent)
            },

            onEditar = { reserva ->

                val intent = Intent(
                    requireContext(),
                    FormReservaActivity::class.java
                )

                intent.putExtra("reserva_id", reserva.id)

                startActivity(intent)
            },

            onEliminar = { reserva ->

                reserva.id?.let {
                    viewModel.delete(it)
                }
            }
        )

        binding.recyclerReservas.layoutManager =
            LinearLayoutManager(requireContext())

        binding.recyclerReservas.adapter =
            adapter

        binding.fabAgregar.setOnClickListener {

            startActivity(
                Intent(
                    requireContext(),
                    FormReservaActivity::class.java
                )
            )
        }

        viewModel.reservas.observe(viewLifecycleOwner) {

            adapter.updateList(it)
        }

        viewModel.load()
    }

    // =========================
    // RECARGAR AL VOLVER
    // =========================

    override fun onResume() {

        super.onResume()

        viewModel.load()
    }
}
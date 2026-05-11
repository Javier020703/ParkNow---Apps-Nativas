package com.example.parknow1.ui.home

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parknow1.R
import com.example.parknow1.data.repository.ParqueaderoRepository
import com.example.parknow1.data.repository.UserRepository
import com.example.parknow1.ui.detail.ParkingDetailFragment
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var tvSaludo: TextView
    private lateinit var recyclerParqueaderos: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        tvSaludo = view.findViewById(R.id.tvSaludo)
        recyclerParqueaderos = view.findViewById(R.id.recyclerParqueaderos)

        recyclerParqueaderos.layoutManager =
            LinearLayoutManager(requireContext())

        cargarUsuario()
        cargarParqueaderos()
    }

    private fun cargarUsuario() {
        lifecycleScope.launch {
            val usuario = UserRepository.obtenerUsuario()
            usuario?.let {
                tvSaludo.text = "¡Hola, ${it.nombres}!"
            }
        }
    }

    private fun cargarParqueaderos() {

        lifecycleScope.launch {

            val lista = ParqueaderoRepository.obtenerParqueaderos()

            recyclerParqueaderos.adapter =
                ParkingAdapter(lista) { parqueadero ->

                    val bundle = Bundle().apply {
                        putString("parqueadero_id", parqueadero.id)
                        putString("nombre", parqueadero.nombre)
                        putString("direccion", parqueadero.direccion)
                        putDouble("tarifa", parqueadero.tarifa_hora)
                        putInt("cupos", parqueadero.cupos_disponibles)
                    }

                    val fragment = ParkingDetailFragment().apply {
                        arguments = bundle
                    }

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.contenedor, fragment)
                        .addToBackStack(null)
                        .commit()
                }
        }
    }
}
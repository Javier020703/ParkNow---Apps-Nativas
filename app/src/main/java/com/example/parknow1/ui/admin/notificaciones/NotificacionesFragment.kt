package com.example.parknow1.ui.admin.notificaciones

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parknow1.R

class NotificacionesFragment : Fragment() {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: AdapterNotificaciones

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_notificaciones, container, false)

        recycler = view.findViewById(R.id.recyclerNotificaciones)

        val lista = mutableListOf(
            Notificacion("Reserva confirmada", "Usuario reservó", "Hace 5 min", "success"),
            Notificacion("Espacio lleno", "No hay cupos", "Hace 10 min", "warning"),
            Notificacion("Error pago", "Pago rechazado", "Hace 20 min", "error")
        )

        adapter = AdapterNotificaciones(lista)

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        return view
    }
}
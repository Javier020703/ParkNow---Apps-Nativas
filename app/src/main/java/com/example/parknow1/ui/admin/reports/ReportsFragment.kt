package com.example.parknow1.ui.admin.reports

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.parknow1.R

class ReportsFragment : Fragment() {

    private lateinit var tvIngresoMes: TextView
    private lateinit var tvIngresoHoy: TextView
    private lateinit var tvPromedio: TextView
    private lateinit var tvReservas: TextView
    private lateinit var tvConversion: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_reports, container, false)

        tvIngresoMes = view.findViewById(R.id.tvIngresoMes)
        tvIngresoHoy = view.findViewById(R.id.tvIngresoHoy)
        tvPromedio = view.findViewById(R.id.tvPromedio)
        tvReservas = view.findViewById(R.id.tvReservas)
        tvConversion = view.findViewById(R.id.tvConversion)

        cargarDatos()

        return view
    }

    private fun cargarDatos() {
        tvIngresoMes.text = "$52,340"
        tvIngresoHoy.text = "$3,450"
        tvPromedio.text = "$2,345"
        tvReservas.text = "1,847"
        tvConversion.text = "68%"
    }
}
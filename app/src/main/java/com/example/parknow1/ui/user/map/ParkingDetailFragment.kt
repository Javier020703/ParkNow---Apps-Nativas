package com.example.parknow1.ui.detail

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.parknow1.R
import com.example.parknow1.ui.reservation.ReservationFragment

class ParkingDetailFragment : Fragment() {

    private lateinit var tvNombre: TextView
    private lateinit var tvDireccion: TextView
    private lateinit var tvPrecio: TextView
    private lateinit var tvCupos: TextView
    private lateinit var btnBack: ImageButton
    private lateinit var btnReservar: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_parking_detail,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        tvNombre = view.findViewById(R.id.tvNombre)
        tvDireccion = view.findViewById(R.id.tvDireccion)
        tvPrecio = view.findViewById(R.id.tvPrecio)
        tvCupos = view.findViewById(R.id.tvCupos)

        btnBack = view.findViewById(R.id.btnBack)
        btnReservar = view.findViewById(R.id.btnReservar)

        // DATA
        val id = arguments?.getString("parqueadero_id") ?: ""
        val nombre = arguments?.getString("nombre") ?: ""
        val direccion = arguments?.getString("direccion") ?: ""
        val tarifa = arguments?.getDouble("tarifa") ?: 0.0
        val cupos = arguments?.getInt("cupos") ?: 0

        // UI
        tvNombre.text = nombre
        tvDireccion.text = "📍 $direccion"
        tvPrecio.text = "$$tarifa / hora"
        tvCupos.text = "🟢 $cupos cupos disponibles"

        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        btnReservar.setOnClickListener {

            val fragment = ReservationFragment()

            val bundle = Bundle().apply {
                putString("parqueadero_id", id)
                putString("parqueadero_nombre", nombre)
                putDouble("tarifa", tarifa)
            }

            fragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.contenedor, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}
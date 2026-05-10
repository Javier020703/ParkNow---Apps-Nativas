package com.example.parknow1.ui.user.reservation

import androidx.fragment.app.Fragment

class ReservationsFragment : Fragment()


/*
package com.example.parknow.ui.user.reservation


//REvisar errores despues

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parknow.R

class ReservationsFragment : Fragment(R.layout.fragment_reservations) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var reservationAdapter: ReservationAdapter
    private lateinit var reservationList: ArrayList<ReservationModel>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerReservations)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        reservationList = arrayListOf(

            ReservationModel(
                "PKN-8542",
                "Parqueadero Centro",
                "Activa",
                "Hoy • 14:30 PM"
            ),

            ReservationModel(
                "PKN-7231",
                "Plaza Principal",
                "Finalizada",
                "15 Feb • 09:00 AM"
            ),

            ReservationModel(
                "PKN-6890",
                "Norte Shopping",
                "Finalizada",
                "10 Feb • 16:20 PM"
            )

        )

        reservationAdapter = ReservationAdapter(reservationList)

        recyclerView.adapter = reservationAdapter
    }
}

 */
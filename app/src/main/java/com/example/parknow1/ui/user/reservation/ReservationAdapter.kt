package com.example.parknow1.ui.user.reservation

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parknow1.R

class ReservationAdapter(
    private val reservations: List<ReservationModel>
) : RecyclerView.Adapter<ReservationAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtCodigo: TextView = view.findViewById(R.id.txtCodigo)
        val txtParking: TextView = view.findViewById(R.id.txtParking)
        val txtEstado: TextView = view.findViewById(R.id.txtEstado)
        val txtFecha: TextView = view.findViewById(R.id.txtFecha)
        val viewStatus: View = view.findViewById(R.id.viewStatus)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reservation, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return reservations.size
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val reservation = reservations[position]

        holder.txtCodigo.text = reservation.codigo
        holder.txtParking.text = reservation.parqueadero
        holder.txtEstado.text = reservation.estado
        holder.txtFecha.text = reservation.fecha

        if (reservation.estado == "Activa") {

            holder.txtEstado.setTextColor(
                Color.parseColor("#2E7D32")
            )

            holder.viewStatus.visibility = View.VISIBLE

        } else {

            holder.txtEstado.setTextColor(
                Color.parseColor("#777777")
            )

            holder.viewStatus.visibility = View.GONE
        }
    }
}
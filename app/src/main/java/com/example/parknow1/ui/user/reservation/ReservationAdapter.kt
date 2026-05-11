package com.example.parknow1.ui.reservation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parknow1.R
import com.example.parknow1.data.model.Reserva

class ReservationAdapter(
    private val lista: List<Reserva>
) : RecyclerView.Adapter<ReservationAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtCodigo: TextView = view.findViewById(R.id.txtCodigo)
        val txtEstado: TextView = view.findViewById(R.id.txtEstado)
        val txtParking: TextView = view.findViewById(R.id.txtParking)
        val txtFecha: TextView = view.findViewById(R.id.txtFecha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reservation, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = lista[position]

        holder.txtCodigo.text = item.id?.take(8) ?: "SIN-CODIGO"

        holder.txtEstado.text = item.estado.replaceFirstChar { it.uppercase() }

        holder.txtParking.text =
            item.nombreParqueadero ?: item.parqueadero

        holder.txtFecha.text = item.hora

        // color estado simple
        if (item.estado == "activa") {
            holder.txtEstado.setTextColor(
                holder.itemView.context.getColor(R.color.azul_oscuro)
            )
        }
    }
}
package com.example.parknow1.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.example.parknow1.R
import com.example.parknow1.data.model.Parqueadero
import com.google.android.material.button.MaterialButton

class ParkingAdapter(
    private val lista: List<Parqueadero>,
    private val onClick: (Parqueadero) -> Unit
) : RecyclerView.Adapter<ParkingAdapter.ViewHolder>() {

    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val tvName: TextView =
            view.findViewById(R.id.tvName)

        val tvLocation: TextView =
            view.findViewById(R.id.tvLocation)

        val tvPrice: TextView =
            view.findViewById(R.id.tvPrice)

        val tvSpots: TextView =
            view.findViewById(R.id.tvSpots)

        val btnDetails: MaterialButton =
            view.findViewById(R.id.btnDetails)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_parking,
                parent,
                false
            )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val parqueadero = lista[position]

        holder.tvName.text =
            parqueadero.nombre

        holder.tvLocation.text =
            "📍 ${parqueadero.direccion}"

        holder.tvPrice.text =
            "💲 $${parqueadero.tarifa_hora} / hora"

        holder.tvSpots.text =
            "🟢 ${parqueadero.cupos_disponibles} cupos"

        holder.btnDetails.setOnClickListener {

            onClick(parqueadero)
        }
    }

    override fun getItemCount(): Int {

        return lista.size
    }
}
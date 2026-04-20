package com.example.parknow.ui.parking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parknow.R
import com.example.parknow.data.model.ParkingSpot

class ParkingAdapter(
    private val list: List<ParkingSpot>,
    private val onClick: (ParkingSpot) -> Unit
) : RecyclerView.Adapter<ParkingAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvLocation: TextView = view.findViewById(R.id.tvLocation)
        val tvPrice: TextView = view.findViewById(R.id.tvPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_parking, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parking = list[position]

        holder.tvName.text = parking.name
        holder.tvLocation.text = parking.location
        holder.tvPrice.text = "$${parking.price}/hora"

        holder.itemView.setOnClickListener {
            onClick(parking)
        }
    }

    override fun getItemCount(): Int = list.size
}
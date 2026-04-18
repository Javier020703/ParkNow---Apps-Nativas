package com.example.parknow1.ui.admin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parknow1.R

class ParqueaderoAdapter : RecyclerView.Adapter<ParqueaderoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.tvNombre)
        val direccion: TextView = view.findViewById(R.id.tvDireccion)
        val cupos: TextView = view.findViewById(R.id.tvCupos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_parqueadero, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = 5 // 👈 SOLO PARA MOSTRAR

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nombre.text = "Parqueadero ${position + 1}"
        holder.direccion.text = "Dirección ${position + 1}"
        holder.cupos.text = "Cupos: ${20 + position}"
    }
}
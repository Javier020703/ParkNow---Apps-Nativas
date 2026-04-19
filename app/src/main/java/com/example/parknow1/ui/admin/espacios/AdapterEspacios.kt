package com.example.parknow1.ui.admin.espacios

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parknow1.R

class AdapterEspacios(
    private val lista: MutableList<EstadoEspacio>
) : RecyclerView.Adapter<AdapterEspacios.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvEspacio: TextView = view.findViewById(R.id.tvEspacio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_espacio, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lista[position]

        // 🔥 CORREGIDO (ANTES DECÍA nombre)
        holder.tvEspacio.text = item.codigo

        when (item.estado) {
            "LIBRE" -> holder.tvEspacio.setBackgroundResource(R.drawable.bg_espacio_libre)
            "OCUPADO" -> holder.tvEspacio.setBackgroundResource(R.drawable.bg_espacio_ocupado)
            "RESERVADO" -> holder.tvEspacio.setBackgroundResource(R.drawable.bg_espacio_reservado)
        }

        // 🔥 CRUD VISUAL
        holder.itemView.setOnClickListener {
            item.estado = when (item.estado) {
                "LIBRE" -> "OCUPADO"
                "OCUPADO" -> "RESERVADO"
                else -> "LIBRE"
            }
            notifyItemChanged(position)
        }
    }
}
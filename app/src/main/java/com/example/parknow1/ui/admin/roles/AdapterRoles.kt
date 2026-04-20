package com.example.parknow1.ui.admin.roles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parknow1.R

class AdapterRoles(
    private val lista: MutableList<Rol>
) : RecyclerView.Adapter<AdapterRoles.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val tvNombreRol: TextView = view.findViewById(R.id.tvNombreRol)
        val tvPermisos: TextView = view.findViewById(R.id.tvPermisos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rol, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = lista[position]

        holder.tvNombreRol.text = item.nombre


        holder.tvPermisos.text = item.permisos.joinToString("\n• ", "• ")

        //  CRUD eliminar
        holder.itemView.setOnLongClickListener {
            lista.removeAt(position)
            notifyDataSetChanged()
            true
        }
    }
}
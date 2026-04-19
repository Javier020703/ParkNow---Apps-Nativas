package com.example.parknow1.ui.admin.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parknow1.R
import com.example.parknow1.ui.admin.activities.FormParqueaderoActivity
import com.example.parknow1.ui.admin.activities.VerParqueaderoActivity
import com.example.parknow1.ui.admin.models.Parqueadero

class ParqueaderoAdapter(
    private val lista: MutableList<Parqueadero>
) : RecyclerView.Adapter<ParqueaderoAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre = view.findViewById<TextView>(R.id.tvNombre)
        val direccion = view.findViewById<TextView>(R.id.tvDireccion)
        val espacios = view.findViewById<TextView>(R.id.tvEspacios)
        val tarifa = view.findViewById<TextView>(R.id.tvTarifa)
        val estado = view.findViewById<TextView>(R.id.tvEstado)

        val editar = view.findViewById<Button>(R.id.btnEditar)
        val ver = view.findViewById<Button>(R.id.btnVer)
        val eliminar = view.findViewById<Button>(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_parqueadero, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = lista[position]

        holder.nombre.text = item.nombre
        holder.direccion.text = item.direccion
        holder.espacios.text = "Espacios: ${item.cupos}"
        holder.tarifa.text = "Tarifa: ${item.tarifa}"


        holder.ver.setOnClickListener {
            val intent = Intent(holder.itemView.context, VerParqueaderoActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }

        holder.editar.setOnClickListener {
            val intent = Intent(holder.itemView.context, FormParqueaderoActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }

        holder.eliminar.setOnClickListener {
            lista.removeAt(position)
            notifyItemRemoved(position)
        }
    }
}
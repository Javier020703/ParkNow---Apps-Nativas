package com.example.parknow1.ui.admin.tarifas

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.parknow1.R

class TarifaAdapter(
    private val lista: MutableList<Tarifa>
) : RecyclerView.Adapter<TarifaAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombre: TextView = view.findViewById(R.id.tvNombre)
        val precio: TextView = view.findViewById(R.id.tvPrecio)
        val descripcion: TextView = view.findViewById(R.id.tvDescripcion)
        val btnEditar: Button = view.findViewById(R.id.btnEditar)
        val btnEliminar: Button = view.findViewById(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tarifa, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tarifa = lista[position]

        holder.nombre.text = tarifa.nombre
        holder.precio.text = tarifa.precio
        holder.descripcion.text = tarifa.descripcion

        holder.btnEliminar.setOnClickListener {
            lista.removeAt(position)
            notifyDataSetChanged()
        }

        holder.btnEditar.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, FormTarifaActivity::class.java)
            intent.putExtra("nombre", tarifa.nombre)
            intent.putExtra("precio", tarifa.precio)
            intent.putExtra("descripcion", tarifa.descripcion)
            context.startActivity(intent)
        }
    }
}
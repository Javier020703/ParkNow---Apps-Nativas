package com.example.parknow1.ui.admin_disabled.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.example.parknow1.R
import com.example.parknow1.data.model.Parqueadero
import com.example.parknow1.ui.admin_disabled.activities.FormParqueaderoActivity
import com.example.parknow1.ui.admin_disabled.activities.VerParqueaderoActivity

class ParqueaderoAdapter(

    private val lista: MutableList<Parqueadero>,
    private val onEliminar: (Parqueadero) -> Unit

) : RecyclerView.Adapter<ParqueaderoAdapter.ViewHolder>() {

    class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val nombre =
            view.findViewById<TextView>(R.id.tvNombre)

        val direccion =
            view.findViewById<TextView>(R.id.tvDireccion)

        val espacios =
            view.findViewById<TextView>(R.id.tvEspacios)

        val tarifa =
            view.findViewById<TextView>(R.id.tvTarifa)

        val estado =
            view.findViewById<TextView>(R.id.tvEstado)

        val editar =
            view.findViewById<Button>(R.id.btnEditar)

        val ver =
            view.findViewById<Button>(R.id.btnVer)

        val eliminar =
            view.findViewById<Button>(R.id.btnEliminar)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_parqueadero,
                parent,
                false
            )

        return ViewHolder(view)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val item = lista[position]

        holder.nombre.text = item.nombre

        holder.direccion.text = item.direccion

        holder.espacios.text =
            "Disponibles: ${item.cupos_disponibles}/${item.cupos_totales}"

        holder.tarifa.text =
            "Tarifa: $${item.tarifa_hora}/hora"

        holder.estado.text =
            item.estado

        // VER
        holder.ver.setOnClickListener {

            val intent = Intent(
                holder.itemView.context,
                VerParqueaderoActivity::class.java
            )

            holder.itemView.context
                .startActivity(intent)
        }

        // EDITAR
        holder.editar.setOnClickListener {

            val intent = Intent(
                holder.itemView.context,
                FormParqueaderoActivity::class.java
            )

            intent.putExtra("id", item.id)
            intent.putExtra("nombre", item.nombre)
            intent.putExtra("direccion", item.direccion)
            intent.putExtra("cupos", item.cupos_totales)
            intent.putExtra("tarifa", item.tarifa_hora)

            holder.itemView.context.startActivity(intent)
        }

        // ELIMINAR
        holder.eliminar.setOnClickListener {

            onEliminar(item)
        }
    }
}
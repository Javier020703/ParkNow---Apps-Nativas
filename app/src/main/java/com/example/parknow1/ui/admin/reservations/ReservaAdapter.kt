package com.example.parknow1.ui.admin.reservations

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.parknow1.R
import com.example.parknow1.databinding.ItemReservaBinding

class ReservaAdapter(
    private val lista: MutableList<Reserva>
) : RecyclerView.Adapter<ReservaAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemReservaBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReservaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val reserva = lista[position]

        //  DATOS PRINCIPALES
        holder.binding.tvCodigo.text = reserva.codigo
        holder.binding.tvCliente.text = reserva.cliente
        holder.binding.tvEstado.text = reserva.estado

        //  INFO EXTRA
        holder.binding.tvInfo.text =
            "${reserva.parqueadero} • ${reserva.espacio} • ${reserva.hora}"

        // COLOR DINÁMICO DEL ESTADO
        val context = holder.itemView.context

        when (reserva.estado) {
            "Activa" -> holder.binding.tvEstado.setTextColor(
                context.getColor(R.color.green)
            )
            "Por vencer" -> holder.binding.tvEstado.setTextColor(
                context.getColor(R.color.orange)
            )
            "Finalizada" -> holder.binding.tvEstado.setTextColor(
                context.getColor(R.color.red)
            )
        }

        // VER DETALLE
        holder.binding.btnVer.setOnClickListener {
            val intent = Intent(context, DetalleReservaActivity::class.java)
            intent.putExtra("pos", position)
            context.startActivity(intent)
        }

        //  EDITAR
        holder.binding.btnEditar.setOnClickListener {
            val intent = Intent(context, FormReservaActivity::class.java)
            intent.putExtra("pos", position)
            context.startActivity(intent)
        }

        // 🔹 ELIMINAR
        holder.binding.btnEliminar.setOnClickListener {
            lista.removeAt(position)
            notifyDataSetChanged()
        }
    }
}
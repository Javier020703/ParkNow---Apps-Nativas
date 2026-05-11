package com.example.parknow1.ui.admin_disabled.reservations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.parknow1.data.model.Reserva
import com.example.parknow1.databinding.ItemReservaBinding

class ReservaAdapter(

    private var lista: List<Reserva>,

    private val onVer: (Reserva) -> Unit,

    private val onEditar: (Reserva) -> Unit,

    private val onEliminar: (Reserva) -> Unit

) : RecyclerView.Adapter<ReservaAdapter.ViewHolder>() {

    inner class ViewHolder(
        val binding: ItemReservaBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val binding = ItemReservaBinding.inflate(

            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val item = lista[position]

        // =========================
        // CODIGO BONITO
        // =========================

        holder.binding.tvCodigo.text =
            "Reserva #${item.id?.take(8) ?: "00000000"}"

        // =========================
        // CLIENTE
        // =========================

        holder.binding.tvCliente.text =
            item.nombreCliente ?: "Cliente"

        // =========================
        // ESTADO
        // =========================

        holder.binding.tvEstado.text =
            item.estado.replaceFirstChar {
                it.uppercase()
            }

        // =========================
        // DETALLE
        // =========================

        holder.binding.tvInfo.text =

            "${item.nombreParqueadero ?: "Parqueadero"} • " +
                    "Espacio ${item.espacio} • " +
                    item.hora

        // =========================
        // BOTONES
        // =========================

        holder.binding.btnVer.setOnClickListener {

            onVer(item)
        }

        holder.binding.btnEditar.setOnClickListener {

            onEditar(item)
        }

        holder.binding.btnEliminar.setOnClickListener {

            onEliminar(item)
        }
    }

    override fun getItemCount() = lista.size

    fun updateList(
        newList: List<Reserva>
    ) {

        lista = newList

        notifyDataSetChanged()
    }
}
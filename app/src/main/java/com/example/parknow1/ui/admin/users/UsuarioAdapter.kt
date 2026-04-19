package com.example.parknow1.ui.admin.users

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.parknow1.databinding.ItemUsuarioBinding

class UsuarioAdapter : RecyclerView.Adapter<UsuarioAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemUsuarioBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUsuarioBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = UsuarioProvider.listaUsuarios.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val user = UsuarioProvider.listaUsuarios[position]

        holder.binding.tvNombre.text = user.nombre
        holder.binding.tvCorreo.text = user.correo
        holder.binding.tvInfo.text =
            "${user.reservas} reservas • $${user.gasto}"

        // VER
        holder.binding.btnVer.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetalleUsuarioActivity::class.java)
            intent.putExtra("pos", position)
            holder.itemView.context.startActivity(intent)
        }

        // EDITAR
        holder.binding.btnEditar.setOnClickListener {
            val intent = Intent(holder.itemView.context, FormUsuarioActivity::class.java)
            intent.putExtra("pos", position)
            holder.itemView.context.startActivity(intent)
        }

        // ELIMINAR
        holder.binding.btnEliminar.setOnClickListener {
            UsuarioProvider.listaUsuarios.removeAt(position)
            notifyDataSetChanged()
        }
    }
}
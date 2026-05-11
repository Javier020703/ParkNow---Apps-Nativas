package com.example.parknow1.ui.admin_disabled.users

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.parknow1.databinding.ItemUsuarioBinding
import com.example.parknow1.data.model.User

class UsuarioAdapter(
    private val lista: MutableList<User>,
    private val onDelete: (User) -> Unit
) : RecyclerView.Adapter<UsuarioAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemUsuarioBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUsuarioBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val user = lista[position]

        holder.binding.tvNombre.text = user.nombres
        holder.binding.tvCorreo.text = user.correo ?: ""
        holder.binding.tvInfo.text = user.rol

        // VER
        holder.binding.btnVer.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetalleUsuarioActivity::class.java)
            intent.putExtra("id", user.id)
            holder.itemView.context.startActivity(intent)
        }

        // EDITAR
        holder.binding.btnEditar.setOnClickListener {
            val intent = Intent(holder.itemView.context, FormUsuarioActivity::class.java)
            intent.putExtra("id", user.id)
            holder.itemView.context.startActivity(intent)
        }

        // ELIMINAR
        holder.binding.btnEliminar.setOnClickListener {
            onDelete(user)
        }
    }
}
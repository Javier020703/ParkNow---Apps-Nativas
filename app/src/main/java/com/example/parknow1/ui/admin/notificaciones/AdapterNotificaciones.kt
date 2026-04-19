package com.example.parknow1.ui.admin.notificaciones

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.parknow1.R

class AdapterNotificaciones(
    private val lista: MutableList<Notificacion>
) : RecyclerView.Adapter<AdapterNotificaciones.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titulo: TextView = view.findViewById(R.id.tvTituloNotif)
        val mensaje: TextView = view.findViewById(R.id.tvMensajeNotif)
        val fecha: TextView = view.findViewById(R.id.tvFechaNotif)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notificacion, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = lista.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = lista[position]

        holder.titulo.text = item.titulo
        holder.mensaje.text = item.mensaje
        holder.fecha.text = item.fecha

        val bg = when (item.tipo) {
            "success" -> R.drawable.bg_notif_success
            "warning" -> R.drawable.bg_notif_warning
            "error" -> R.drawable.bg_notif_error
            else -> android.R.color.white
        }

        holder.itemView.setBackgroundResource(bg)
    }

    //  CRUD
    fun agregar(n: Notificacion) {
        lista.add(0, n)
        notifyItemInserted(0)
    }

    fun eliminar(pos: Int) {
        lista.removeAt(pos)
        notifyItemRemoved(pos)
    }

    fun actualizar(pos: Int, n: Notificacion) {
        lista[pos] = n
        notifyItemChanged(pos)
    }
}
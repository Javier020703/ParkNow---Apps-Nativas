package com.example.parknow1.ui.admin.espacios

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parknow1.R

class EspaciosFragment : Fragment() {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: AdapterEspacios
    private lateinit var tvResumen: TextView
    private lateinit var btnActualizar: Button
    private lateinit var buscador: EditText

    private val lista = mutableListOf<EstadoEspacio>()
    private var listaOriginal = mutableListOf<EstadoEspacio>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_espacios, container, false)

        recycler = view.findViewById(R.id.recyclerEspacios)
        tvResumen = view.findViewById(R.id.tvResumen)
        btnActualizar = view.findViewById(R.id.btnActualizar)
        buscador = view.findViewById(R.id.etBuscarEspacio)

        generarEspacios()
        listaOriginal = lista.toMutableList()

        adapter = AdapterEspacios(lista)
        recycler.layoutManager = GridLayoutManager(requireContext(), 5)
        recycler.adapter = adapter

        actualizarResumen()

        btnActualizar.setOnClickListener {
            actualizarResumen()
        }

        // 🔍 BUSCADOR
        buscador.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {

                val filtrados = listaOriginal.filter {
                    it.codigo.contains(text.toString(), true)
                }

                lista.clear()
                lista.addAll(filtrados)

                adapter.notifyDataSetChanged()
                actualizarResumen()
            }
        })

        return view
    }

    private fun generarEspacios() {
        lista.clear()

        for (i in 1..15) {
            lista.add(EstadoEspacio("A-$i", "LIBRE"))
        }
    }

    private fun actualizarResumen() {
        val libres = lista.count { it.estado == "LIBRE" }
        val ocupados = lista.count { it.estado == "OCUPADO" }
        val reservados = lista.count { it.estado == "RESERVADO" }

        tvResumen.text = "Libres: $libres | Ocupados: $ocupados | Reservados: $reservados"
    }
}
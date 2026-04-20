package com.example.parknow1.ui.admin.users

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parknow1.databinding.FragmentUsuariosBinding

class UsuariosFragment : Fragment() {

    private var _binding: FragmentUsuariosBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: UsuarioAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentUsuariosBinding.inflate(inflater, container, false)

        adapter = UsuarioAdapter()

        binding.recyclerUsuarios.layoutManager = LinearLayoutManager(context)
        binding.recyclerUsuarios.adapter = adapter

        binding.fabAgregar.setOnClickListener {
            startActivity(Intent(requireContext(), FormUsuarioActivity::class.java))
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged() //Sirve para refrescar
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
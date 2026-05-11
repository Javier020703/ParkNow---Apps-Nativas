package com.example.parknow1.ui.admin_disabled.users

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.parknow1.databinding.FragmentUsuariosBinding
import kotlinx.coroutines.launch
import com.example.parknow1.data.model.User
import com.example.parknow1.data.repository.UserRepository

class UsuariosFragment : Fragment() {

    private var _binding: FragmentUsuariosBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: UsuarioAdapter
    private val lista = mutableListOf<User>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        _binding = FragmentUsuariosBinding.inflate(inflater, container, false)

        adapter = UsuarioAdapter(lista) { user ->
            lifecycleScope.launch {
                UserRepository.eliminarUsuario(user.id)
                cargarUsuarios()
            }
        }

        binding.recyclerUsuarios.layoutManager = LinearLayoutManager(context)
        binding.recyclerUsuarios.adapter = adapter

        binding.fabAgregar.setOnClickListener {
            startActivity(Intent(requireContext(), FormUsuarioActivity::class.java))
        }

        cargarUsuarios()

        return binding.root
    }

    private fun cargarUsuarios() {
        lifecycleScope.launch {
            lista.clear()
            lista.addAll(UserRepository.obtenerUsuarios())
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        cargarUsuarios()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
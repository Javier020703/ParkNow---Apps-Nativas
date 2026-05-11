package com.example.parknow1.ui.profile

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.request.CachePolicy
import coil.transform.CircleCropTransformation
import com.example.parknow1.R
import com.example.parknow1.data.repository.UserRepository
import com.example.parknow1.ui.user.profile.EditProfileFragment
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imgProfile = view.findViewById<ImageView>(R.id.imgProfile)
        val txtUserName = view.findViewById<TextView>(R.id.txtUserName)
        val txtUserEmail = view.findViewById<TextView>(R.id.txtUserEmail)
        val btnEditProfile = view.findViewById<MaterialButton>(R.id.btnEditProfile)

        lifecycleScope.launch {

            try {

                val usuario = UserRepository.obtenerUsuario()

                if (usuario != null) {

                    txtUserName.text =
                        "${usuario.nombres} ${usuario.apellidos}"

                    txtUserEmail.text =
                        usuario.correo ?: "Sin correo"

                    // FOTO PERFIL
                    if (!usuario.foto_url.isNullOrEmpty()) {

                        val url =
                            "${usuario.foto_url}?t=${System.currentTimeMillis()}"

                        imgProfile.load(url) {
                            transformations(CircleCropTransformation())

                            placeholder(R.drawable.ic_profile_placeholder)
                            error(R.drawable.ic_profile_placeholder)

                            memoryCachePolicy(CachePolicy.DISABLED)
                            diskCachePolicy(CachePolicy.DISABLED)
                        }

                    } else {
                        imgProfile.setImageResource(R.drawable.ic_profile_placeholder)
                    }

                } else {

                    Toast.makeText(
                        requireContext(),
                        "No se pudo cargar el perfil",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            } catch (e: Exception) {

                Toast.makeText(
                    requireContext(),
                    "Error: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // EDITAR PERFIL
        btnEditProfile.setOnClickListener {

            parentFragmentManager.beginTransaction()
                .replace(R.id.contenedor, EditProfileFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}
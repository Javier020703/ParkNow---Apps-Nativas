package com.example.parknow1.ui.user.profile

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.transform.CircleCropTransformation
import com.example.parknow1.R
import com.example.parknow1.data.repository.UserRepository
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch
import java.io.File

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    private var uriFoto: Uri? = null
    private lateinit var imgProfile: ImageView
    private lateinit var tempFile: File

    // PERMISO CÁMARA
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) openCamera()
            else Toast.makeText(requireContext(), "Permiso requerido", Toast.LENGTH_SHORT).show()
        }

    // CÁMARA
    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { ok ->
            if (ok) {
                uriFoto = Uri.fromFile(tempFile)
                imgProfile.load(uriFoto) {
                    transformations(CircleCropTransformation())
                }
            }
        }

    // GALERÍA
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                uriFoto = it
                imgProfile.load(it) {
                    transformations(CircleCropTransformation())
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 🔥 IDs CORRECTOS según tu XML
        val etNames = view.findViewById<EditText>(R.id.etNames)
        val etLastNames = view.findViewById<EditText>(R.id.etLastNames)
        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val etPhone = view.findViewById<EditText>(R.id.etPhone)

        val btnSave = view.findViewById<com.google.android.material.button.MaterialButton>(R.id.btnSaveChanges)

        imgProfile = view.findViewById(R.id.imgEditProfile)
        val btnCamera = view.findViewById<ImageView>(R.id.imgEditProfile) // botón cámara lo tienes dentro del layout visual

        // 📌 CARGAR PERFIL
        lifecycleScope.launch {
            val user = UserRepository.obtenerUsuario()

            user?.let {
                etNames.setText(it.nombres)
                etLastNames.setText(it.apellidos)
                etEmail.setText(it.correo ?: "")
                etPhone.setText(it.telefono ?: "")

                if (!it.foto_url.isNullOrEmpty()) {
                    imgProfile.load(it.foto_url) {
                        transformations(CircleCropTransformation())
                    }
                }
            }
        }

        // 📸 CAMBIAR FOTO
        btnCamera.setOnClickListener {
            showImageOptions()
        }

        // 💾 GUARDAR
        btnSave.setOnClickListener {

            val nombres = etNames.text.toString().trim()
            val apellidos = etLastNames.text.toString().trim()
            val correo = etEmail.text.toString().trim()
            val telefono = etPhone.text.toString().trim()

            if (nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty()) {
                Toast.makeText(requireContext(), "Completa los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {

                    var fotoUrl: String? = null

                    uriFoto?.let {
                        fotoUrl = UserRepository.subirFotoPerfil(requireContext(), it)
                    }

                    UserRepository.actualizarPerfil(
                        nombres,
                        apellidos,
                        correo,
                        fotoUrl
                    )

                    Toast.makeText(requireContext(), "Perfil actualizado", Toast.LENGTH_SHORT).show()
                    parentFragmentManager.popBackStack()

                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showImageOptions() {
        val options = arrayOf("Tomar foto", "Galería")

        android.app.AlertDialog.Builder(requireContext())
            .setItems(options) { _, which ->
                when (which) {
                    0 -> checkPermission()
                    1 -> galleryLauncher.launch("image/*")
                }
            }
            .show()
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openCamera()
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun openCamera() {
        val folder = File(requireContext().cacheDir, "images")
        folder.mkdirs()

        tempFile = File(folder, "profile.jpg")

        val uri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.fileprovider",
            tempFile
        )

        cameraLauncher.launch(uri)
    }
}
    package com.example.parknow1.ui.admin_disabled.activities

    import android.os.Bundle
    import android.widget.Button
    import android.widget.EditText
    import android.widget.Toast

    import androidx.appcompat.app.AppCompatActivity
    import androidx.lifecycle.lifecycleScope

    import com.example.parknow1.R
    import com.example.parknow1.data.model.Parqueadero
    import com.example.parknow1.data.repository.ParqueaderoRepository

    import kotlinx.coroutines.launch

    class FormParqueaderoActivity : AppCompatActivity() {

        private lateinit var etNombre: EditText
        private lateinit var etDireccion: EditText
        private lateinit var etCupos: EditText
        private lateinit var etTarifa: EditText

        private var parqueaderoId: String? = null

        private lateinit var btnGuardar: Button

        override fun onCreate(savedInstanceState: Bundle?) {

            super.onCreate(savedInstanceState)

            setContentView(R.layout.activity_form_parqueadero)

            etNombre = findViewById(R.id.etNombre)
            etDireccion = findViewById(R.id.etDireccion)
            etCupos = findViewById(R.id.etCupos)
            etTarifa = findViewById(R.id.etTarifa)

            btnGuardar = findViewById(R.id.btnGuardar)

            btnGuardar.setOnClickListener {

                guardarParqueadero()
            }

            parqueaderoId = intent.getStringExtra("id")

            if (parqueaderoId != null) {

                etNombre.setText(intent.getStringExtra("nombre"))
                etDireccion.setText(intent.getStringExtra("direccion"))
                etCupos.setText(intent.getIntExtra("cupos", 0).toString())
                etTarifa.setText(intent.getDoubleExtra("tarifa", 0.0).toString())

                btnGuardar.text = "Actualizar parqueadero"
            }
        }

        private fun guardarParqueadero() {

            val nombre = etNombre.text.toString().trim()
            val direccion = etDireccion.text.toString().trim()
            val cupos = etCupos.text.toString().toIntOrNull() ?: 0
            val tarifa = etTarifa.text.toString().toDoubleOrNull() ?: 0.0

            lifecycleScope.launch {

                try {

                    if (parqueaderoId == null) {

                        //  CREAR
                        val parqueadero = Parqueadero(
                            id = null,
                            nombre = nombre,
                            direccion = direccion,
                            descripcion = "",
                            cupos_totales = cupos,
                            cupos_disponibles = cupos,
                            tarifa_hora = tarifa,
                            caracteristicas = listOf("Vigilancia", "Techado"),
                            estado = "activo"
                        )

                        ParqueaderoRepository.crearParqueadero(parqueadero)

                    } else {

                        // EDITAR
                        val parqueadero = Parqueadero(
                            id = parqueaderoId,
                            nombre = nombre,
                            direccion = direccion,
                            descripcion = "",
                            cupos_totales = cupos,
                            cupos_disponibles = cupos,
                            tarifa_hora = tarifa,
                            caracteristicas = listOf("Vigilancia", "Techado"),
                            estado = "activo"
                        )

                        ParqueaderoRepository.actualizarParqueadero(parqueadero)
                    }

                    runOnUiThread {
                        Toast.makeText(this@FormParqueaderoActivity, "Guardado", Toast.LENGTH_SHORT).show()
                        finish()
                    }

                } catch (e: Exception) {

                    runOnUiThread {
                        Toast.makeText(this@FormParqueaderoActivity, e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
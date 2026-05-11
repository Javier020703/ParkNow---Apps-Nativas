package com.example.parknow1.ui.user.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast

import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.parknow1.R
import com.example.parknow1.data.repository.ParqueaderoRepository
import com.example.parknow1.data.model.Parqueadero
import com.example.parknow1.ui.home.ParkingAdapter
import com.example.parknow1.ui.detail.ParkingDetailFragment

import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback {

    private lateinit var rvParqueaderos: RecyclerView
    private var googleMap: GoogleMap? = null

    private var latUsuario: Double? = null
    private var lonUsuario: Double? = null

    private var parqueaderos = listOf<Parqueadero>()

    // ---------------- PERMISO ----------------
    private val permisoUbicacion =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { ok ->
            if (ok) obtenerUbicacion()
            else Toast.makeText(
                requireContext(),
                "Permiso de ubicación requerido",
                Toast.LENGTH_SHORT
            ).show()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvParqueaderos = view.findViewById(R.id.rvParqueaderos)
        rvParqueaderos.layoutManager =
            LinearLayoutManager(requireContext())

        // MAPA
        val mapFragment = SupportMapFragment.newInstance()
        childFragmentManager.beginTransaction()
            .replace(R.id.map_container, mapFragment)
            .commit()

        mapFragment.getMapAsync(this)

        cargarParqueaderos()

        if (tienePermiso()) {
            obtenerUbicacion()
        } else {
            permisoUbicacion.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    // ---------------- MAP READY ----------------
    override fun onMapReady(map: GoogleMap) {

        googleMap = map
        map.uiSettings.isZoomControlsEnabled = true

        map.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(4.6097, -74.0817),
                12f
            )
        )
    }

    // ---------------- DATA ----------------
    private fun cargarParqueaderos() {

        lifecycleScope.launch {

            try {

                parqueaderos =
                    ParqueaderoRepository.obtenerParqueaderos()

                rvParqueaderos.adapter =
                    ParkingAdapter(parqueaderos) { p ->

                        val bundle = Bundle().apply {
                            putString("parqueadero_id", p.id)
                            putString("nombre", p.nombre)
                            putString("direccion", p.direccion)
                            putDouble("tarifa", p.tarifa_hora)
                            putInt("cupos", p.cupos_disponibles)
                        }

                        parentFragmentManager.beginTransaction()
                            .replace(
                                R.id.contenedor,
                                ParkingDetailFragment().apply {
                                    arguments = bundle
                                }
                            )
                            .addToBackStack(null)
                            .commit()
                    }

                mostrarEnMapa()

            } catch (e: Exception) {

                Toast.makeText(
                    requireContext(),
                    "Error cargando parqueaderos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // ---------------- PERMISO CHECK ----------------
    private fun tienePermiso(): Boolean {

        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    // ---------------- UBICACIÓN ----------------
    private fun obtenerUbicacion() {

        if (!tienePermiso()) return

        lifecycleScope.launch {

            try {

                val client =
                    LocationServices
                        .getFusedLocationProviderClient(requireActivity())

                val location =
                    client.getCurrentLocation(
                        Priority.PRIORITY_HIGH_ACCURACY,
                        null
                    ).await()

                location?.let {

                    latUsuario = it.latitude
                    lonUsuario = it.longitude

                    mostrarEnMapa()
                }

            } catch (e: SecurityException) {

                Toast.makeText(
                    requireContext(),
                    "Sin permisos de ubicación",
                    Toast.LENGTH_SHORT
                ).show()

            } catch (e: Exception) {

                Toast.makeText(
                    requireContext(),
                    "Error obteniendo ubicación",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // ---------------- MAP ----------------
    private fun mostrarEnMapa() {

        val map = googleMap ?: return

        map.clear()

        // usuario
        val usuario = latUsuario?.let { lat ->
            lonUsuario?.let { lon ->
                LatLng(lat, lon)
            }
        }

        usuario?.let {

            map.addMarker(
                MarkerOptions()
                    .position(it)
                    .title("Tu ubicación")
                    .icon(
                        BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_AZURE
                        )
                    )
            )

            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(it, 13f)
            )
        }

        // parqueaderos
        parqueaderos.forEach { p ->

            val lat = p.latitud ?: return@forEach
            val lon = p.longitud ?: return@forEach
            val pos = LatLng(lat, lon)

            map.addMarker(
                MarkerOptions()
                    .position(pos)
                    .title(p.nombre)
                    .snippet(p.direccion)
                    .icon(
                        BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_RED
                        )
                    )
            )
        }
    }
}
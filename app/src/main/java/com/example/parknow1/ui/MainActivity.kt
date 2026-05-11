package com.example.parknow1.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope

import com.example.parknow1.R
import com.example.parknow1.data.remote.SupabaseClient
import com.example.parknow1.ui.auth.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

import com.example.parknow1.ui.home.HomeFragment
import com.example.parknow1.ui.profile.ProfileFragment
import com.example.parknow1.ui.reservation.ReservationsFragment
import com.example.parknow1.ui.user.map.MapFragment

import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val drawer =
            findViewById<DrawerLayout>(R.id.drawerLayout)

        val btnMenu =
            findViewById<ImageButton>(R.id.btnMenu)

        val bottomNav =
            findViewById<BottomNavigationView>(R.id.bottomNavigation)

        val navigationView =
            findViewById<NavigationView>(R.id.navigationView)

        // ABRIR DRAWER
        btnMenu.setOnClickListener {

            drawer.open()
        }

        // FRAGMENT INICIAL
        cambiarFragment(HomeFragment())

        // BOTTOM NAVIGATION
        bottomNav.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.nav_home -> {
                    cambiarFragment(HomeFragment())
                }

                R.id.nav_map -> {
                    cambiarFragment(MapFragment())
                }

                R.id.nav_reservas -> {
                    cambiarFragment(ReservationsFragment())
                }

                R.id.nav_profile -> {
                    cambiarFragment(ProfileFragment())
                }
            }

            true
        }

        // MENU LATERAL
        navigationView.setNavigationItemSelectedListener {

            when (it.itemId) {

                R.id.nav_home -> {
                    cambiarFragment(HomeFragment())
                }

                R.id.nav_reservas -> {
                    cambiarFragment(ReservationsFragment())
                }

                R.id.nav_map -> {
                    cambiarFragment(MapFragment())
                }

                R.id.nav_profile -> {
                    cambiarFragment(ProfileFragment())
                }

                R.id.nav_logout -> {
                    cerrarSesion()
                }
            }

            drawer.close()

            true
        }
    }

    private fun cambiarFragment(fragment: Fragment) {

        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedor, fragment)
            .commit()
    }

    private fun cerrarSesion() {

        lifecycleScope.launch {

            try {

                SupabaseClient.client.auth.signOut()

                runOnUiThread {

                    Toast.makeText(
                        this@MainActivity,
                        "Sesión cerrada",
                        Toast.LENGTH_SHORT
                    ).show()

                    startActivity(
                        Intent(
                            this@MainActivity,
                            LoginActivity::class.java
                        )
                    )

                    finish()
                }

            } catch (e: Exception) {

                runOnUiThread {

                    Toast.makeText(
                        this@MainActivity,
                        "Error al cerrar sesión",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
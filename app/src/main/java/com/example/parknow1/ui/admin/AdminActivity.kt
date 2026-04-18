package com.example.parknow1.ui.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.parknow1.R
import com.example.parknow1.ui.admin.fragments.*
import com.google.android.material.navigation.NavigationView

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val navView = findViewById<NavigationView>(R.id.navView)

        // Pantalla inicial
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameContainer, DashboardFragment())
            .commit()

        navView.setNavigationItemSelectedListener {

            val fragment = when (it.itemId) {
                R.id.nav_dashboard -> DashboardFragment()
                R.id.nav_parqueaderos -> ParqueaderosFragment()
                R.id.nav_usuarios -> UsuariosFragment()
                else -> DashboardFragment()
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.frameContainer, fragment)
                .commit()

            true
        }
    }
}
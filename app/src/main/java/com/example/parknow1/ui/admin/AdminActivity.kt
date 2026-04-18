package com.example.parknow1.ui.admin

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.widget.Toolbar
import com.example.parknow1.R
import com.example.parknow1.ui.admin.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        // 🔹 REFERENCIAS
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val navView = findViewById<NavigationView>(R.id.navView)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        // 🔹 TOOLBAR + HAMBURGUESA
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open,
            R.string.close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // 🔹 FRAGMENT INICIAL
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameContainer, DashboardFragment())
            .commit()

        // 🔹 MENÚ LATERAL (DRAWER)
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

            drawerLayout.closeDrawers()
            true
        }

        // 🔹 MENÚ INFERIOR (BOTTOM NAV)
        bottomNav.setOnItemSelectedListener {

            val fragment = when (it.itemId) {
                R.id.bottom_dashboard -> DashboardFragment()
                R.id.bottom_parqueaderos -> ParqueaderosFragment()
                R.id.bottom_usuarios -> UsuariosFragment()
                else -> DashboardFragment()
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.frameContainer, fragment)
                .commit()

            true
        }
    }
}
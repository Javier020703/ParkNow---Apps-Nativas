package com.example.parknow1.ui.admin

import android.graphics.Color
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.parknow1.R
import com.example.parknow1.ui.admin.roles.RolesFragment
import com.example.parknow1.ui.admin.notificaciones.NotificacionesFragment
// FRAGMENTS
import com.example.parknow1.ui.admin.fragments.DashboardFragment
import com.example.parknow1.ui.admin.fragments.ParqueaderosFragment
import com.example.parknow1.ui.admin.reservations.ReservasFragment
import com.example.parknow1.ui.admin.users.UsuariosFragment
import com.example.parknow1.ui.admin.tarifas.TarifasFragment
import com.example.parknow1.ui.admin.espacios.EspaciosFragment

import com.example.parknow1.ui.admin.reports.ReportsFragment
import com.example.parknow1.ui.admin.heatmap.HeatmapFragment
import com.example.parknow1.ui.admin.settings.SettingsFragment

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class AdminActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var navView: NavigationView
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        drawerLayout = findViewById(R.id.drawerLayout)
        toolbar = findViewById(R.id.toolbar)
        navView = findViewById(R.id.navView)
        bottomNav = findViewById(R.id.bottomNav)

        configurarToolbar()
        configurarDrawer()
        configurarBottomNav()
        manejarBack()

        if (savedInstanceState == null) {
            abrirFragment(DashboardFragment(), "Dashboard")
            navView.setCheckedItem(R.id.nav_dashboard)
            bottomNav.selectedItemId = R.id.bottom_dashboard
        }
    }

    // ===============================
    //  TOOLBAR
    // ===============================
    private fun configurarToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Admin Panel"
        toolbar.setTitleTextColor(Color.WHITE)
    }


    //  DRAWER

    private fun configurarDrawer() {

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open,
            R.string.close
        )

        toggle.drawerArrowDrawable.color = Color.WHITE
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener {

            val (fragment, titulo, bottomItem) = when (it.itemId) {

                R.id.nav_dashboard ->
                    Triple(DashboardFragment(), "Dashboard", R.id.bottom_dashboard)

                R.id.nav_parqueaderos ->
                    Triple(ParqueaderosFragment(), "Parqueaderos", R.id.bottom_parqueaderos)

                R.id.nav_reservas ->
                    Triple(ReservasFragment(), "Reservas", -1)

                R.id.nav_usuarios ->
                    Triple(UsuariosFragment(), "Usuarios", R.id.bottom_usuarios)

                R.id.nav_tarifas ->
                    Triple(TarifasFragment(), "Tarifas", -1)

                R.id.nav_espacios ->
                    Triple(EspaciosFragment(), "Espacios Tiempo Real", -1)


                R.id.nav_roles ->
                    Triple(RolesFragment(), "Roles", -1)

                R.id.nav_notificaciones ->
                    Triple(NotificacionesFragment(), "Notificaciones", -1)


                R.id.nav_reports ->
                    Triple(ReportsFragment(), "Reportes Financieros", -1)

                R.id.nav_heatmap ->
                    Triple(HeatmapFragment(), "Mapa de Calor", -1)

                R.id.nav_settings ->
                    Triple(SettingsFragment(), "Configuración", -1)

                else ->
                    Triple(DashboardFragment(), "Dashboard", R.id.bottom_dashboard)
            }

            abrirFragment(fragment, titulo)

            // sincroniza bottom nav
            if (bottomItem != -1) {
                bottomNav.selectedItemId = bottomItem
            } else {
                bottomNav.menu.setGroupCheckable(0, false, true)
            }

            drawerLayout.closeDrawers()
            true
        }
    }


    //  BOTTOM NAV
    private fun configurarBottomNav() {

        bottomNav.setOnItemSelectedListener {

            val (fragment, titulo, drawerItem) = when (it.itemId) {

                R.id.bottom_dashboard ->
                    Triple(DashboardFragment(), "Dashboard", R.id.nav_dashboard)

                R.id.bottom_parqueaderos ->
                    Triple(ParqueaderosFragment(), "Parqueaderos", R.id.nav_parqueaderos)

                R.id.bottom_usuarios ->
                    Triple(UsuariosFragment(), "Usuarios", R.id.nav_usuarios)

                else ->
                    Triple(DashboardFragment(), "Dashboard", R.id.nav_dashboard)
            }

            abrirFragment(fragment, titulo)
            navView.setCheckedItem(drawerItem)

            true
        }
    }


    //  CAMBIO DE FRAGMENT

    private fun abrirFragment(fragment: Fragment, titulo: String) {

        supportFragmentManager.beginTransaction()
            .replace(R.id.frameContainer, fragment)
            .commit()

        supportActionBar?.title = titulo
    }

    //  BACK BUTTON

    private fun manejarBack() {

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                when {
                    drawerLayout.isDrawerOpen(navView) -> {
                        drawerLayout.closeDrawers()
                    }

                    supportFragmentManager.backStackEntryCount > 0 -> {
                        supportFragmentManager.popBackStack()
                    }

                    else -> finish()
                }
            }
        })
    }
}

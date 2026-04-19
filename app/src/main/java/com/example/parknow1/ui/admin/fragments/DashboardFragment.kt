package com.example.parknow1.ui.admin.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.parknow1.R

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnReportes = view.findViewById<Button>(R.id.btnReportes)

        btnReportes.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.frameContainer, ReportesFragment())
                .commit()
        }
    }
}
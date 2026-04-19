package com.example.parknow1.ui.admin.settings

import android.os.Bundle
import android.view.*
import android.widget.Switch
import androidx.fragment.app.Fragment
import com.example.parknow1.R

class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        val switchNotif = view.findViewById<Switch>(R.id.switchNotif)
        val switchAuto = view.findViewById<Switch>(R.id.switchAuto)

        switchNotif.setOnCheckedChangeListener { _, isChecked ->
            // CRUD visual
        }

        switchAuto.setOnCheckedChangeListener { _, isChecked ->
        }

        return view
    }
}
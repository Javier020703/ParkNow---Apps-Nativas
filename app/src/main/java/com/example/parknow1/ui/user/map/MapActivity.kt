package com.example.parknow.ui.map

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.parknow.R
import com.example.parknow.ui.parking.ParkingDetailActivity

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val pin1 = findViewById<ImageView>(R.id.pin1)
        val pin2 = findViewById<ImageView>(R.id.pin2)
        val pinMain = findViewById<ImageView>(R.id.pinMain)
        val btnBack = findViewById<TextView>(R.id.btnBack)

        // 🔹 Click en pines
        pin1.setOnClickListener {
            startActivity(Intent(this, ParkingDetailActivity::class.java))
        }

        pin2.setOnClickListener {
            startActivity(Intent(this, ParkingDetailActivity::class.java))
        }

        pinMain.setOnClickListener {
            startActivity(Intent(this, ParkingDetailActivity::class.java))
        }

        // 🔹 Volver
        btnBack.setOnClickListener {
            finish()
        }
    }
}
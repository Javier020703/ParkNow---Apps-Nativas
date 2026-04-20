package com.example.parknow.ui.parking

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.parknow.R
import com.example.parknow.ui.reservation.ReservationActivity

class ParkingDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking_detail)

        val btnReserve = findViewById<Button>(R.id.btnReserve)

        btnReserve.setOnClickListener {
            val intent = Intent(this, ReservationActivity::class.java)
            startActivity(intent)
        }
    }
}
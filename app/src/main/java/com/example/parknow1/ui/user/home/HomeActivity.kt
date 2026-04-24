package com.example.parknow1.ui.user.home


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parknow.data.model.ParkingSpot
import com.example.parknow1.ui.user.map.MapActivity
import com.example.parknow1.ui.user.parking.ParkingAdapter
import com.example.parknow1.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val recycler = findViewById<RecyclerView>(R.id.recyclerParking)

        val parkingList = listOf(
            ParkingSpot("Centro Comercial", "Calle 10", 5000.0, true),
            ParkingSpot("Plaza Principal", "Av 15", 3000.0, true),
            ParkingSpot("Parking Norte", "Av 20", 7000.0, false)
        )

        recycler.layoutManager = LinearLayoutManager(this)

        recycler.adapter = ParkingAdapter(parkingList) { parking ->

            val intent = Intent(this, MapActivity::class.java)
            startActivity(intent)
        }
    }
}
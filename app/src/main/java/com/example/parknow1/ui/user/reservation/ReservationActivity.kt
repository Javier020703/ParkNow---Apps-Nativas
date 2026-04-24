package com.example.parknow1.ui.user.reservation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.parknow1.R
import com.example.parknow1.ui.user.payment.PaymentActivity

class ReservationActivity : AppCompatActivity() {

    private val pricePerHour = 4.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        val spDuration = findViewById<Spinner>(R.id.spDuration)
        val tvSubtotal = findViewById<TextView>(R.id.tvSubtotal)
        val btnContinue = findViewById<Button>(R.id.btnContinue)

        val options = arrayOf("1 hora", "2 horas", "3 horas", "4 horas")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spDuration.adapter = adapter

        spDuration.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val hours = position + 1
                val subtotal = hours * pricePerHour
                tvSubtotal.text = "Subtotal: $${subtotal}"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        btnContinue.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        }
    }
}
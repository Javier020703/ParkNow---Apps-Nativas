package com.example.parknow.ui.reservation

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.parknow.R
import com.example.parknow.ui.payment.PaymentActivity

class ReservationActivity : AppCompatActivity() {

    private val pricePerHour = 4.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        val spDuration = findViewById<Spinner>(R.id.spDuration)
        val tvSubtotal = findViewById<TextView>(R.id.tvSubtotal)
        val btnContinue = findViewById<Button>(R.id.btnContinue)

        // Opciones del spinner
        val options = arrayOf("1 hora", "2 horas", "3 horas", "4 horas")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spDuration.adapter = adapter

        // Cambio de selección
        spDuration.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val hours = position + 1
                val subtotal = hours * pricePerHour
                tvSubtotal.text = "Subtotal: $${subtotal}"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Botón continuar
        btnContinue.setOnClickListener {
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        }
    }
}
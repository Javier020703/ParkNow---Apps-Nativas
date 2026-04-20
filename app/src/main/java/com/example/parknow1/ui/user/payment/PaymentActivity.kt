package com.example.parknow.ui.payment

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.parknow.R

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        val btnPay = findViewById<Button>(R.id.btnPay)

        btnPay.setOnClickListener {
            Toast.makeText(this, "Pago realizado con éxito", Toast.LENGTH_LONG).show()

            // Opcional: regresar al inicio
            finishAffinity()
        }
    }
}
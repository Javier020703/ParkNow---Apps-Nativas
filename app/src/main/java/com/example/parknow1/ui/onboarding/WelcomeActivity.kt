package com.example.parknow1.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.parknow1.R
import com.example.parknow1.ui.auth.RegisterActivity
import android.os.Handler
import android.os.Looper

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val btnComenzar = findViewById<Button>(R.id.btnComenzar)


        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }, 2000)
    }
}
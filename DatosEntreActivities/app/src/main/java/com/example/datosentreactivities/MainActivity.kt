package com.example.datosentreactivities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        val input: EditText = findViewById(R.id.editText)
        val btnVer: Button = findViewById(R.id.btnVer)

        btnVer.setOnClickListener{
            val intento1=Intent(this,Actividad2::class.java)
            intento1.putExtra("direccion",input.text.toString())
            startActivity(intento1)
        }

    }
}
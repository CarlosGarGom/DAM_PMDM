package com.example.contadorbotones

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Declaramos una variable para el contador
    private var contador: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Referencias a los elementos del diseño
        val textView: TextView = findViewById(R.id.contador)
        val btnSumar: Button = findViewById(R.id.btnSumar)
        val btnRestar: Button = findViewById(R.id.btnRestar)
        val btnReset: Button = findViewById(R.id.btnReset)

        // Inicializamos el TextView con el valor inicial del contador
        textView.text = contador.toString()

        // Funcionalidad para el botón "Sumar"
        btnSumar.setOnClickListener {
            contador++
            textView.text = contador.toString()
        }

        // Funcionalidad para el botón "Restar"
        btnRestar.setOnClickListener {
            contador--
            textView.text = contador.toString()
        }

        // Funcionalidad para el botón "Resetear"
        btnReset.setOnClickListener {
            contador = 0
            textView.text = contador.toString()
        }
    }
}
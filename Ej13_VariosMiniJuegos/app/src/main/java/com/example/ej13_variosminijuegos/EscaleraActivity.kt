package com.example.ej13_variosminijuegos

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class EscaleraActivity: AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_escalera)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar);

        val editTextSteps = findViewById<EditText>(R.id.editTextSteps)
        val btnGenerate = findViewById<Button>(R.id.btnGenerate)
        val escaleraContainer = findViewById<LinearLayout>(R.id.escaleraContainer)
        btnGenerate.setOnClickListener {
            val stepsText = editTextSteps.text.toString()
            if (stepsText.isEmpty()) {
                Toast.makeText(this, "Por favor, introduce un número.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val steps = stepsText.toIntOrNull()
            if (steps == null || steps == 0) {
                Toast.makeText(this, "Introduce un número distinto de 0.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Limpia el contenedor antes de dibujar la nueva escalera
            escaleraContainer.removeAllViews()

            if (steps > 0) {
                // Escalera ascendente
                for (i in 1..steps) {
                    val rowLayout = LinearLayout(this).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        orientation = LinearLayout.HORIZONTAL
                    }

                    // Agrega espacios vacíos (hueco a la izquierda)
                    for (space in 1..(steps - i)) {
                        val emptySpace = ImageView(this).apply {
                            layoutParams = LinearLayout.LayoutParams(100, 100)
                        }
                        rowLayout.addView(emptySpace)
                    }

                    // Agrega los escalones (cuadrados)
                    for (star in 1..i) {
                        val step = ImageView(this).apply {
                            layoutParams = LinearLayout.LayoutParams(100, 100)
                            setImageResource(R.drawable.escalon) // Imagen del escalón
                        }
                        rowLayout.addView(step)
                    }

                    // Añade la fila completa al contenedor principal
                    escaleraContainer.addView(rowLayout)
                }
            } else {
                // Escalera descendente
                for (i in 1..-steps) {
                    val rowLayout = LinearLayout(this).apply {
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        orientation = LinearLayout.HORIZONTAL
                    }

                    // Agrega espacios vacíos (hueco a la izquierda)
                    for (space in 1..(i - 1)) {
                        val emptySpace = ImageView(this).apply {
                            layoutParams = LinearLayout.LayoutParams(100, 100)
                        }
                        rowLayout.addView(emptySpace)
                    }

                    // Agrega los escalones (cuadrados) con el efecto de espejo
                    for (star in 1..(-steps - i + 1)) {
                        val step = ImageView(this).apply {
                            layoutParams = LinearLayout.LayoutParams(100, 100)
                            setImageResource(R.drawable.escalon) // Imagen del escalón
                            scaleX = -1f // Aplica el modo espejo
                        }
                        rowLayout.addView(step)
                    }

                    // Añade la fila completa al contenedor principal
                    escaleraContainer.addView(rowLayout)
                }
            }
        }
    }
    // Inflar el menú
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_navigation, menu)
        return true
    }
    // Manejar la selección de elementos del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_tres_en_raya -> {
                val intent = Intent(this, TresEnRayaActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                return true
            }
            R.id.menu_escalera -> {
                val intent = Intent(this, EscaleraActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                return true
            }
            R.id.menu_primos -> {
                val intent = Intent(this, PrimosActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}

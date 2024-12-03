package com.example.ej01_examen_carlos

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class TrianguloPascalActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_triangulo)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val trianguloContainer = findViewById<LinearLayout>(R.id.trianguloContainer)
        //val tvResults = findViewById<TextView>(R.id.tvResults)
        val bundle = intent.extras
        val dato = bundle?.getString("lado")

        if (dato.isNullOrEmpty()) {
            val errorTextView = TextView(this).apply {
                text = "Por favor, introduce un número válido."
                textSize = 16f
            }
            trianguloContainer.addView(errorTextView)
        } else {
            try {
                val filas = dato.toInt()

                if (filas <= 0) {
                    val errorTextView = TextView(this).apply {
                        text = "El número debe ser mayor que cero."
                        textSize = 16f
                    }
                    trianguloContainer.addView(errorTextView)
                } else {
                    dibujarTrianguloPascal(filas, trianguloContainer)
                }
            } catch (e: NumberFormatException) {
                val errorTextView = TextView(this).apply {
                    text = "Por favor, introduce un número válido."
                    textSize = 16f
                }
                trianguloContainer.addView(errorTextView)
            }
        }

        setSupportActionBar(toolbar)
    }


    fun dibujarTrianguloPascal(n: Int, trianguloContainer: LinearLayout) {
        // Limpia el contenedor antes de dibujar
        trianguloContainer.removeAllViews()

        if (n <= 0) {
            val errorTextView = TextView(this).apply {
                text = "El número de filas debe ser mayor que cero."
                textSize = 16f
            }
            trianguloContainer.addView(errorTextView)
            return
        }

        val triangulo = Array(n) { IntArray(it + 1) }

        // Construcción del triángulo de Pascal
        for (i in triangulo.indices) {
            triangulo[i][0] = 1
            triangulo[i][i] = 1
            for (j in 1 until i) {
                triangulo[i][j] = triangulo[i - 1][j - 1] + triangulo[i - 1][j]
            }
        }

        for (i in triangulo.indices) {
            val rowLayout = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                setPadding(0, 8, 0, 8) // Espaciado entre filas
            }

            // Espacio inicial para centrar la fila
            val space = (n - i) * 30
            rowLayout.setPadding(space, 0, space, 0)

            for (j in triangulo[i].indices) {
                val numberTextView = TextView(this).apply {
                    text = triangulo[i][j].toString()
                    textSize = 18f
                    setPadding(16, 0, 16, 0) // Espacio entre números
                }
                rowLayout.addView(numberTextView)
            }

            trianguloContainer.addView(rowLayout)
        }
    }



}
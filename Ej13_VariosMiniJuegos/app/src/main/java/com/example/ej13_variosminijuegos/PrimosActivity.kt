package com.example.ej13_variosminijuegos

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class PrimosActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_primos)
        val toolbar: Toolbar =findViewById(R.id.toolbar)
        setSupportActionBar(toolbar);

        val etRangeStart = findViewById<EditText>(R.id.etRangeStart)
        val etRangeEnd = findViewById<EditText>(R.id.etRangeEnd)
        val btnFindTwins = findViewById<Button>(R.id.btnFindTwins)
        val tvResults = findViewById<TextView>(R.id.tvResults)

        btnFindTwins.setOnClickListener {
            val startText = etRangeStart.text.toString()
            val endText = etRangeEnd.text.toString()

            if (startText.isEmpty() || endText.isEmpty()) {
                Toast.makeText(this, "Por favor, introduce ambos valores.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val start = startText.toIntOrNull()
            val end = endText.toIntOrNull()

            if (start == null || end == null || start >= end) {
                Toast.makeText(
                    this,
                    "Introduce un rango válido (inicio < fin).",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val twinPrimes = findTwinPrimes(start, end)
            tvResults.text = if (twinPrimes.isEmpty()) {
                "No se encontraron pares gemelos en el rango."
            } else {
                "Pares de primos gemelos:\n" + twinPrimes.joinToString("\n") { "(${it.first}, ${it.second})" }
            }
        }

    }
    private fun findTwinPrimes(start: Int, end: Int): List<Pair<Int, Int>> {
        val primes = mutableListOf<Int>()

        // Generar todos los números primos en el rango
        for (num in start..end) {
            if (isPrime(num)) {
                primes.add(num)
            }
        }

        // Encontrar pares gemelos
        val twinPrimes = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until primes.size - 1) {
            if (primes[i + 1] - primes[i] == 2) {
                twinPrimes.add(Pair(primes[i], primes[i + 1]))
            }
        }
        return twinPrimes
    }

    /**
     * Verifica si un número es primo.
     * @param num Número a verificar.
     * @return true si es primo; false si no lo es.
     */
    private fun isPrime(num: Int): Boolean {
        if (num < 2) return false
        for (i in 2..Math.sqrt(num.toDouble()).toInt()) {
            if (num % i == 0) return false
        }
        return true
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

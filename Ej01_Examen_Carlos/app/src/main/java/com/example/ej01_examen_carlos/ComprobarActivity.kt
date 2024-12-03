package com.example.ej01_examen_carlos

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class ComprobarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_comprobar)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val bundle = intent.extras
        val dato = bundle?.getString("numero")
        val tvResults = findViewById<TextView>(R.id.tvResults)

        setSupportActionBar(toolbar)

        if (dato != null) {
            val numero = dato.toInt()

            val prime = isPrime(numero)
            val par = isPar(numero)
            val fibonacci = isFibonacci(numero)

            val resultado = StringBuilder()
            resultado.append("$numero: ")


            if (prime) {
                resultado.append("es primo,")
            } else {
                resultado.append("no es primo,")
            }


            if (par) {
                resultado.append("es par y ")
            } else {
                resultado.append("es impar y ")
            }


            if (fibonacci) {
                resultado.append("Pertenece a la serie de Fibonacci.")
            } else {
                resultado.append("No pertenece a la serie de Fibonacci.")
            }


            tvResults.text = resultado.toString()
        } else {
            tvResults.text = "No se recibió un número válido."
        }
    }

    private fun isFibonacci(numero: Int): Boolean {
        if (numero < 0) return false

        val isPerfectSquare = { n: Int ->
            val sqrt = Math.sqrt(n.toDouble()).toInt()
            sqrt * sqrt == n
        }

        return isPerfectSquare(5 * numero * numero + 4) || isPerfectSquare(5 * numero * numero - 4)
    }

    private fun isPrime(num: Int): Boolean {
        if (num < 2) return false
        for (i in 2..Math.sqrt(num.toDouble()).toInt()) {
            if (num % i == 0) return false
        }
        return true
    }

    private fun isPar(num: Int): Boolean {
        return num % 2 == 0
    }
    // Inflar el menú
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_navigation, menu)
        return true
    }
}

package com.example.ej01_examen_carlos

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar =findViewById(R.id.toolbar)
        val numero: EditText= findViewById(R.id.etNumber)
        val comprobar: Button= findViewById(R.id.btnCheck)
        val dibujar:Button= findViewById(R.id.btnDraw)
        val lado: EditText= findViewById(R.id.etLado)
        setSupportActionBar(toolbar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        comprobar.setOnClickListener{
            val numeroString = numero.text.toString()
            if (numeroString.isEmpty()) {
                Toast.makeText(this, "Por favor, introduce un numero.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, ComprobarActivity::class.java)
            intent.putExtra("numero",numero.text.toString())
            startActivity(intent)
        }
        dibujar.setOnClickListener{
            val ladoString = lado.text.toString()
            if (ladoString.isEmpty()) {
                Toast.makeText(this, "Por favor, introduce un valor.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val intent = Intent(this, TrianguloPascalActivity::class.java)
            intent.putExtra("lado", ladoString) // Pasar el valor introducido
            startActivity(intent)
        }
    }

    // Inflar el menú
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_navigation, menu)
        return true
    }
}
package com.example.ej13_variosminijuegos

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Botón Tres en raya
        findViewById<Button>(R.id.btnTresEnRaya).setOnClickListener {
            val intent = Intent(this, TresEnRayaActivity::class.java)
            startActivity(intent)
        }

        // Botón Escalera
        findViewById<Button>(R.id.btnEscalera).setOnClickListener {
            val intent = Intent(this, EscaleraActivity::class.java)
            startActivity(intent)
        }

        // Botón Primos
        findViewById<Button>(R.id.btnPrimos).setOnClickListener {
            val intent = Intent(this, PrimosActivity::class.java)
            startActivity(intent)
        }
        //val toolbar: Toolbar =findViewById(R.id.toolbar)
        //setSupportActionBar(toolbar);
    }

    // Inflar el menú
    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
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
    }*/
}
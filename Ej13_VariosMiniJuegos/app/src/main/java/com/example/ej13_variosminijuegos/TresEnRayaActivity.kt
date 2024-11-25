package com.example.ej13_variosminijuegos

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.get
import android.widget.LinearLayout


class TresEnRayaActivity: AppCompatActivity() {

    private val gameController = GameController()
    private lateinit var tablero: Array<Array<Char>>
    private var gameOver = false
    private var turnoJugador1 = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tres_en_raya)
        val toolbar: Toolbar =findViewById(R.id.toolbar)
        setSupportActionBar(toolbar);
        // Bloquear orientación horizontal
        //requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        //Ocultar UI
        CommonFunctions().hideSystemUI(window);



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
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            // Mantener la UI oculta al volver a la actividad
            CommonFunctions().hideSystemUI(window)
        }
    }
}

package com.example.ej07_graficosandroid
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class Graficos2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitvity_graficos2)


        val toolbar: Toolbar =findViewById(R.id.toolbar)
        setSupportActionBar(toolbar);
        supportActionBar?.title = "Graficos (II)"
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_graficos1 -> {
                // Inicia la actividad para gráficos1
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_graficos2 -> {
                // Inicia la actividad para gráficos2
                val intent = Intent(this, Graficos2Activity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_dibujarImagen -> {
                // Inicia la actividad para dibujar imagen
                val intent = Intent(this, DibujarImagenActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_exit -> {
                // Acción para salir
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}


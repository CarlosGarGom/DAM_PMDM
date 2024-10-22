package com.example.ej07_graficosandroid
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout

class DibujarImagenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.acitvity_dibujar_imagen)


        val toolbar: Toolbar =findViewById(R.id.toolbar)
        setSupportActionBar(toolbar);
        supportActionBar?.title = "Dibujar Imagen"

      /*  val fondo = Lienzo(this)
        val layout1=findViewById<ConstraintLayout>(R.id.layout1)
        layout1.addView(fondo)*/
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
/*class Lienzo(context: Context) : View(context){

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRGB(255,255,255)
        val pincel =Paint()
        pincel.setARGB(255,255,0,0)
        canvas.drawRect(10f,10f,(width-10).toFloat(),40f,pincel)

        pincel.setStyle(Paint.Style.STROKE)
        canvas.drawRect(10f,60f,(width-10).toFloat(),40f,pincel)

        pincel.setStrokeWidth(3f)
        canvas.drawRect(10f,110f,(width-10).toFloat(),140f,pincel)
    }
}*/

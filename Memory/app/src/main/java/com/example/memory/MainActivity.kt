package com.example.memory
import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper

import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.Bitmap
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.BitmapDrawable
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat


import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var portadaLayout: LinearLayout
    private lateinit var botonIniciar: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        portadaLayout = findViewById(R.id.portadaLayout)
        botonIniciar = findViewById(R.id.btn_jugar)
        botonIniciar.setOnClickListener {
            val intent = Intent(this, DifficultyActivity::class.java)
            startActivity(intent)
            finish() // Opcional: Termina la actividad principal si no quieres volver a ella.
        }
        val toolbar: Toolbar =findViewById(R.id.toolbar)
        setSupportActionBar(toolbar);

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Escalar el ícono de navegación
        val originalIcon = ContextCompat.getDrawable(this, R.drawable.carta_atras) as BitmapDrawable
        val scaledIcon = BitmapDrawable(resources, Bitmap.createScaledBitmap(originalIcon.bitmap, 40, 60, true))
        supportActionBar?.setHomeAsUpIndicator(scaledIcon)

    }


}
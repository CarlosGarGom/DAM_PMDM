package com.example.memory

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.memory.GameActivity
import com.example.memory.R
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat


class DifficultyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty)

        findViewById<Button>(R.id.btnFacil).setOnClickListener {
            startGameWithDifficulty("facil")
        }

        findViewById<Button>(R.id.btnNormal).setOnClickListener {
            startGameWithDifficulty("normal")
        }

        findViewById<Button>(R.id.btnDificil).setOnClickListener {
            startGameWithDifficulty("dificil")
        }
        val toolbar: Toolbar =findViewById(R.id.toolbar)
        setSupportActionBar(toolbar);

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Escalar el ícono de navegación
        val originalIcon = ContextCompat.getDrawable(this, R.drawable.carta_atras) as BitmapDrawable
        val scaledIcon = BitmapDrawable(resources, Bitmap.createScaledBitmap(originalIcon.bitmap, 40, 60, true))
        supportActionBar?.setHomeAsUpIndicator(scaledIcon)
    }

    private fun startGameWithDifficulty(difficulty: String) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("DIFICULTAD", difficulty)
        startActivity(intent)
    }
}

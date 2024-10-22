package com.example.ej06_creandomenu

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View

// Vista personalizada
class CustomView2 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()


    private val textPaint = Paint().apply {
        color = Color.RED
        textSize = 40f
        isAntiAlias = true
    }

    private val path = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


        // Configurar el Paint para el círculo
        paint.color = Color.YELLOW
        paint.style = Paint.Style.FILL // Relleno
        paint.isAntiAlias = true // Suavizar bordes

        // Dibujar un círculo
        val centerX = width / 2f
        val centerY = (height / 2f)
        val radius = width / 2f
        canvas.drawCircle(centerX, centerY, radius, paint)

        // Cambiar el color del Paint para el óvalo
        paint.color = Color.BLACK
        paint.style = Paint.Style.STROKE // Relleno
        paint.isAntiAlias = true
        paint.strokeWidth = 10f
        // Dibujar un óvalo
        val left = 0f
        val top = 0f
        val right = width
        val bottom = height
        canvas.drawOval(left, top, right.toFloat(), bottom.toFloat(), paint)
    }


}
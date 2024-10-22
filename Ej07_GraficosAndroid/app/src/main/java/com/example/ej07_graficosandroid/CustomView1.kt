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
class CustomView1 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint().apply {
        color = Color.RED
        strokeWidth = 5f
        style = Paint.Style.STROKE
    }

    private val textPaint = Paint().apply {
        color = Color.RED
        textSize = 40f
        isAntiAlias = true
    }

    private val path = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Dibujar los rectángulos
        paint.style = Paint.Style.FILL
        canvas.drawRect(50f, 150f, 1000f, 200f, paint)
        paint.style = Paint.Style.STROKE
        for (i in 2..3) {
            canvas.drawRect(50f, (100f + i * 50), 1000f, (150f + i * 50), paint)
        }

        // Dibujar el texto en camino curvo
        path.moveTo(0f, 1000f)
        path.quadTo(200f, 900f, 400f, 850f)
        canvas.drawTextOnPath("Hola Mundo Rotado", path, 0f, 0f, textPaint)

        // Dibujar varios textos en una columna
        val lines = listOf("Hola Mundo (SERIF)", "Hola Mundo (SANS SERIF)", "Hola Mundo (MONOSPACE)", "Hola Mundo (SERIF ITALIC)", "Hola Mundo (SERIF ITALIC BOLD)")
        val fonts = listOf(
            Typeface.SERIF,
            Typeface.SANS_SERIF,
            Typeface.MONOSPACE,
            Typeface.create(Typeface.SERIF, Typeface.ITALIC),
            Typeface.create(Typeface.SERIF, Typeface.BOLD_ITALIC)
        )
        for (i in lines.indices) {
            textPaint.typeface = fonts[i]
            canvas.drawText(lines[i], 50f, 1200f + i * 50, textPaint)
        }
    }


}
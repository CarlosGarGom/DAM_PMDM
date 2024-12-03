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
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.widget.Toast


import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var portadaLayout: LinearLayout
    private lateinit var botonIniciar: ImageView
    private lateinit var gridLayout: GridLayout
    private lateinit var cronometroTextView: TextView
    private var cardButtons = mutableListOf<Button>()
    private var cards = mutableListOf<Card>()
    private var firstSelectedCard: Card? = null
    private var secondSelectedCard: Card? = null
    private var isProcessing = false
    private lateinit var botonVolver: ImageView;
    private var countDownTimer: CountDownTimer? = null
    private var tiempoRestante: Long = 45000 // 1 minuto en milisegundos
    private var isTimerRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        portadaLayout = findViewById(R.id.portadaLayout)
        botonIniciar = findViewById(R.id.btn_jugar)
        gridLayout = findViewById(R.id.gridLayout)
        cronometroTextView = findViewById(R.id.cronometro)
        botonVolver = findViewById(R.id.btn_volver)

        botonIniciar.setOnClickListener {
            // Oculta la portada y muestra el juego
            botonVolver.visibility = ImageView.GONE
            portadaLayout.visibility = LinearLayout.GONE
            gridLayout.visibility = GridLayout.VISIBLE
            cronometroTextView.visibility = TextView.VISIBLE

            // Inicia el juego
            initializeGame()
            startTimer() // Inicia el cronómetro al comenzar el juego
            // Realiza la animación de voltear todas las cartas al principio
            flipAllCards()
        }
    }
    private fun initializeGame() {
        val images = listOf(
            R.drawable.asi_minijuego_3_vasito, R.drawable.asi_minijuego_3_copa_negra_brunida, R.drawable.asi_minijuego_3_jarra_de_pico, R.drawable.asi_minijuego_3_copa_tumba_75,
            R.drawable.asi_minijuego_3_sonajero_redondo, R.drawable.asi_minijuego_3_tres_en_raya, R.drawable.asi_minijuego_3_tapadera_zoomorfo, R.drawable.asi_minijuego_3_tintinnabulum
        )

        val cardImages = (images + images).shuffled()

        // Define el tamaño fijo de cada carta
        val cardSize = 200  // Ajusta según el tamaño que necesites en dp
        for (i in 0 until 16) {
            val card = Card(id = i, imageResId = cardImages[i])
            cards.add(card)

            val button = Button(this).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = cardSize
                    height = cardSize + 50
                    setMargins(20, 20, 20, 20)  // Espacio entre cartas
                }
                setBackgroundResource(R.drawable.asi_minijuego_3_trasero)
                setOnClickListener { onCardClicked(i) }
            }
            cardButtons.add(button)
            gridLayout.addView(button)
        }
    }

    private fun startTimer() {
        // Cancelamos cualquier temporizador previo antes de iniciarlo
        countDownTimer?.cancel()

        // Inicia el temporizador desde el tiempo restante actual
        countDownTimer = object : CountDownTimer(tiempoRestante, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tiempoRestante = millisUntilFinished
                updateTimerUI() // Actualizamos la UI del cronómetro en cada tick
            }

            override fun onFinish() {
                showGameOver("¡Has perdido!")
            }
        }.start()
        isTimerRunning = true
    }

    private fun updateTimerUI() {
        val segundos = (tiempoRestante / 1000).toInt()
        val minutos = segundos / 60
        val segundosRestantes = segundos % 60
        cronometroTextView.text = String.format("%02d:%02d", minutos, segundosRestantes)
    }

    private fun onCardClicked(position: Int) {
        if (isProcessing) return // Evitar múltiples clics mientras se verifica

        val card = cards[position]
        val button = cardButtons[position]

        // Si la carta ya está boca arriba o emparejada, no hacemos nada
        if (card.isFaceUp || card.isMatched) return

        // Voltear la carta y mostrar la imagen
        card.isFaceUp = true
        //button.setBackgroundResource(card.imageResId) // Mostrar imagen de la carta

        if (firstSelectedCard == null) {
            firstSelectedCard = card
            flipCard(button, card.imageResId) // Animación para voltear la carta
        } else if (secondSelectedCard == null) {
            secondSelectedCard = card
            flipCard(button, card.imageResId) // Animación para voltear la carta
            isProcessing = true
            checkForMatch()
        }

    }

    private fun checkForMatch() {
        // Usamos un Handler para retrasar el chequeo de coincidencia
        Handler(Looper.getMainLooper()).postDelayed({
            if (firstSelectedCard?.imageResId == secondSelectedCard?.imageResId) {
                // Las cartas coinciden
                firstSelectedCard?.isMatched = true
                secondSelectedCard?.isMatched = true
                addTime() // Agregar 10 segundos al cronómetro
                // Iluminar las cartas emparejadas
                firstSelectedCard?.let { card ->
                    val button = cardButtons[cards.indexOf(card)]
                    illuminatePiece(button)
                }
                secondSelectedCard?.let { card ->
                    val button = cardButtons[cards.indexOf(card)]
                    illuminatePiece(button)}
            } else {
                // No coinciden, las volvemos a ocultar usando la animación
                firstSelectedCard?.let { card ->
                    val button = cardButtons[cards.indexOf(card)]
                    card.isFaceUp = false
                    flipCard(button, R.drawable.asi_minijuego_3_trasero, isReversing = true)
                }
                secondSelectedCard?.let { card ->
                    val button = cardButtons[cards.indexOf(card)]
                    card.isFaceUp = false
                    flipCard(button, R.drawable.asi_minijuego_3_trasero, isReversing = true)
                }
            }

            // Reset de selección para la siguiente jugada
            firstSelectedCard = null
            secondSelectedCard = null
            isProcessing = false

            checkForGameEnd()
        }, 1000) // Tiempo de espera antes de voltear las cartas de nuevo (1 segundo)
    }


    private fun checkForGameEnd() {
        // Verifica si todas las cartas están emparejadas
        if (cards.all { it.isMatched }) {
            isTimerRunning = false  // Detenemos el cronómetro
            countDownTimer?.cancel() // Cancelamos el temporizador
            showGameOver("¡Has ganado!")
        }
    }

    private fun addTime() {
        // Agregar 10 segundos al cronómetro y continuar desde el nuevo tiempo
        tiempoRestante += 5000
        updateTimerUI() // Actualiza el UI del cronómetro inmediatamente

        // Cancelamos y reiniciamos el temporizador para que continúe desde el nuevo tiempo
        startTimer()
    }

    private fun showGameOver(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
        botonVolver.visibility = ImageView.VISIBLE

        botonVolver.setOnClickListener{
            val intent = Intent(this@Minijuego3, MapActivity::class.java)
            startActivity(intent)
            finish()
        }


        gridLayout.removeAllViews() // Limpiar las vistas del grid
    }

    override fun onBackPressed() {
        // Detenemos el temporizador si está en ejecución
        countDownTimer?.cancel()
        isTimerRunning = false

        // Llama al metodo de la superclase para asegurarte de que la actividad se cierre correctamente
        super.onBackPressed()
    }
    private fun flipCard(button: Button, imageResId: Int, isReversing: Boolean = false) {
        val scale = applicationContext.resources.displayMetrics.density
        button.cameraDistance = 8000 * scale

        val flipOut = ObjectAnimator.ofFloat(button, "rotationY", 0f, 90f).apply {
            duration = 300
        }

        val flipIn = ObjectAnimator.ofFloat(button, "rotationY", 90f, 0f).apply {
            duration = 300
        }

        // Listener para manejar el cambio de la imagen al final del flipOut
        flipOut.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                // Cambia la imagen después de completar la primera parte de la animación
                button.setBackgroundResource(if (isReversing) R.drawable.asi_minijuego_3_trasero else imageResId)
                // Inicia la segunda parte del giro para completar la animación
                flipIn.start()
            }
        })

        flipOut.start()
    }

    private fun illuminatePiece(button: Button) {
        // Creamos un ColorMatrix para aumentar el brillo
        val matrix = ColorMatrix()
        matrix.setScale(1.5f, 1.5f, 1.5f, 1f)  // Aumentamos el brillo multiplicando por 1.5

        // Creamos un filtro de color a partir del ColorMatrix
        val colorFilter = ColorMatrixColorFilter(matrix)

        // Aplicamos el filtro de color al fondo del botón
        button.background.colorFilter = colorFilter

        // Usamos un ValueAnimator para hacer la transición de vuelta al brillo original
        val animator = ValueAnimator.ofFloat(1.5f, 1f) // Animamos del brillo aumentado a normal
        animator.duration = 1500 // Duración de la animación (2000ms)

        animator.addUpdateListener { animation ->
            // Obtenemos el valor intermedio de la animación
            val value = animation.animatedValue as Float
            // Creamos un nuevo ColorMatrix con el valor intermedio
            val scaleMatrix = ColorMatrix()
            scaleMatrix.setScale(value, value, value, 1f) // Ajustamos el brillo

            // Aplicamos el filtro de color con el nuevo brillo
            button.background.colorFilter = ColorMatrixColorFilter(scaleMatrix)
        }

        // Inicia la animación
        animator.start()
    }
    private fun flipAllCards() {
        // Voltear todas las cartas para mostrar la imagen durante 2 segundos
        cardButtons.forEachIndexed { index, button ->
            val card = cards[index]
            card.isFaceUp = true
            flipCard(button, card.imageResId) // Voltear para mostrar la imagen
        }

        // Esperar 2 segundos y luego voltear todas las cartas de nuevo
        Handler(Looper.getMainLooper()).postDelayed({
            cardButtons.forEachIndexed { index, button ->
                val card = cards[index]
                card.isFaceUp = false
                flipCard(button, R.drawable.asi_minijuego_3_trasero, isReversing = true) // Volver a la cara hacia abajo
            }
        }, 2000) // 2 segundos
    }

}
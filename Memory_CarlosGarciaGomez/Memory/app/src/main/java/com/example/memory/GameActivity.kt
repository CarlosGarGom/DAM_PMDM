package com.example.memory

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

class GameActivity : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout
    private lateinit var cronometroTextView: TextView
    private lateinit var botonVolver: Button
    private var cardButtons = mutableListOf<Button>()
    private var cards = mutableListOf<Card>()
    private var firstSelectedCard: Card? = null
    private var secondSelectedCard: Card? = null
    private var isProcessing = false
    private var countDownTimer: CountDownTimer? = null
    private var tiempoRestante: Long = 45000 // 45 segundos
    private var isTimerRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        gridLayout = findViewById(R.id.gridLayout)
        cronometroTextView = findViewById(R.id.cronometro)
        botonVolver = findViewById(R.id.btn_volver)

        botonVolver.visibility = ImageView.GONE
        gridLayout.visibility = GridLayout.VISIBLE
        cronometroTextView.visibility = TextView.VISIBLE
        val dificultad = intent.getStringExtra("DIFICULTAD") ?: "facil"
        val images = getImagesForDifficulty(dificultad)

        initializeGame(images)
        startTimer()
        flipAllCards()

        botonVolver.setOnClickListener {
            finish()
        }

        val toolbar: Toolbar =findViewById(R.id.toolbar)
        setSupportActionBar(toolbar);

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Escalar el ícono de navegación
        val originalIcon = ContextCompat.getDrawable(this, R.drawable.carta_atras) as BitmapDrawable
        val scaledIcon = BitmapDrawable(resources, Bitmap.createScaledBitmap(originalIcon.bitmap, 40, 60, true))
        supportActionBar?.setHomeAsUpIndicator(scaledIcon)
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // Guardar el estado de las cartas (si están volteadas o emparejadas)
        val cardStates = cards.map { card ->
            Pair(card.isFaceUp, card.isMatched)  // Guardamos si están volteadas y si están emparejadas
        }
        outState.putSerializable("cardStates", ArrayList(cardStates))

        // Guardar el tiempo restante
        outState.putLong("tiempoRestante", tiempoRestante)

        // Guardar si el temporizador está corriendo
        outState.putBoolean("isTimerRunning", isTimerRunning)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // Restaurar el estado de las cartas
        val cardStates = savedInstanceState.getSerializable("cardStates") as? ArrayList<Pair<Boolean, Boolean>>
        cardStates?.let {
            cards.forEachIndexed { index, card ->
                val (isFaceUp, isMatched) = it[index]
                card.isFaceUp = isFaceUp
                card.isMatched = isMatched

                // Si la carta está volteada, actualizamos la UI
                val button = cardButtons[index]
                if (isFaceUp) {
                    flipCard(button, card.imageResId)
                } else {
                    flipCard(button, R.drawable.carta_atras, isReversing = true)
                }
            }
        }

        // Restaurar el tiempo restante y el estado del temporizador
        tiempoRestante = savedInstanceState.getLong("tiempoRestante", 45000)
        isTimerRunning = savedInstanceState.getBoolean("isTimerRunning", false)

        // Si el temporizador estaba corriendo, reiniciamos el temporizador
        if (isTimerRunning) {
            startTimer()
        } else {
            updateTimerUI()
        }
    }

    override fun onPause() {
        super.onPause()
        // Cancelamos el temporizador si la actividad se va a pausar
        countDownTimer?.cancel()
    }


    private fun initializeGame(images: List<Int>) {
        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)
        gridLayout.removeAllViews() // Limpiar las cartas anteriores, si las hay

        // Duplicar las imágenes para formar pares y barajar
        val cardImages = (images + images).shuffled()
        cards.clear()
        cardButtons.clear()

        // Ajustar las dimensiones del GridLayout
        val totalCards = cardImages.size
        val columnCount = 4
        val rowCount = totalCards / columnCount
        gridLayout.columnCount = columnCount
        gridLayout.rowCount = rowCount

        // Define el tamaño fijo de cada carta (en dp convertido a píxeles)
        val cardSize = 180  // 180 dp
        val marginSize = 20 // 20 dp

        // Crear las cartas
        for (i in 0 until totalCards) {
            val card = Card(id =



            i, imageResId = cardImages[i])
            cards.add(card)

            val button = Button(this).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = cardSize
                    height = cardSize + 50 // Añade espacio extra si lo necesitas
                    setMargins(marginSize, marginSize, marginSize, marginSize)
                }
                setBackgroundResource(R.drawable.carta_atras) // Imagen del reverso
                setOnClickListener { onCardClicked(i) }
            }
            cardButtons.add(button)
            gridLayout.addView(button)
        }
    }

    private fun getImagesForDifficulty(dificultad: String): List<Int> {
        val allImages = listOf(
            R.drawable.carta_goma, R.drawable.carta_cuaderno, R.drawable.carta_estuche,
            R.drawable.carta_mundo, R.drawable.carta_pregamento, R.drawable.carta_regla,
            R.drawable.carta_tijeras, R.drawable.carta_mochila, R.drawable.carta_lapiz,
            R.drawable.carta_cinta, R.drawable.carta_libro, R.drawable.carta_calculadora
        )

        return when (dificultad) {
            "facil" -> allImages.shuffled().take(4)  // 8 cartas (4 pares)
            "normal" -> allImages.shuffled().take(6) // 12 cartas (6 pares)
            "dificil" -> allImages.shuffled().take(8) // 16 cartas (8 pares)
            else -> allImages.shuffled().take(4)
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
                showGameOver(getString(R.string.perdido))
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
                    flipCard(button, R.drawable.carta_atras, isReversing = true)
                }
                secondSelectedCard?.let { card ->
                    val button = cardButtons[cards.indexOf(card)]
                    card.isFaceUp = false
                    flipCard(button, R.drawable.carta_atras, isReversing = true)
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
            showGameOver(getString(R.string.ganado))
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
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            //finish()
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
                button.setBackgroundResource(if (isReversing) R.drawable.carta_atras else imageResId)
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
                flipCard(button, R.drawable.carta_atras, isReversing = true) // Volver a la cara hacia abajo
            }
        }, 2000) // 2 segundos
    }

}

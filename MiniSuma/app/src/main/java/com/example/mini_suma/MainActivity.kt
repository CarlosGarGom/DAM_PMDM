package com.example.mini_suma

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast

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
        // Referenciar elementos de la UI
        val inputNumero1: EditText = findViewById(R.id.numero1)
        val inputNumero2: EditText = findViewById(R.id.numero2)
        val radioGroup: RadioGroup = findViewById(R.id.Operacion)
        val btnOperar: Button = findViewById(R.id.buttonOperar)
        val textViewResultado: TextView = findViewById(R.id.resultado)

        // Escuchar clic en el botón Operar
        btnOperar.setOnClickListener {
            // Obtener los números ingresados
            val numero1 = inputNumero1.text.toString().toDoubleOrNull()
            val numero2 = inputNumero2.text.toString().toDoubleOrNull()

            // Verificar si los números son válidos
            if (numero1 == null || numero2 == null) {
                Toast.makeText(this, "Por favor, ingrese números válidos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verificar qué operación ha sido seleccionada
            val selectedOperationId = radioGroup.checkedRadioButtonId

            // Calcular el resultado según la operación seleccionada
            val resultado = when (selectedOperationId) {
                R.id.radioButtonOperacion -> numero1 + numero2 // Sumar
                R.id.radioButtonRestar -> numero1 - numero2 // Restar
                else -> {
                    Toast.makeText(this, "Seleccione una operación", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            // Mostrar el resultado en el TextView
            textViewResultado.text = "Resultado: $resultado"
        }
    }
}
package com.example.ej09_fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            val bundle = bundleOf(
                "NAME_BUNDLE" to "Mi nombre",
                "ADDRESS_BUNDLE" to "mi casa"
            )

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<BlankFragment>(R.id.fragmentContainer, args = bundle)
            }
        }
    }
}

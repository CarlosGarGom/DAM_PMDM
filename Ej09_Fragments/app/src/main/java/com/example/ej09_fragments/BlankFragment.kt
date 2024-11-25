package com.example.ej09_fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class BlankFragment : Fragment() {

    private var name: String? = null
    private var address: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString(NAME_BUNDLE)
            address = it.getString(ADDRESS_BUNDLE)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño del fragmento
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        // Obtener el Bundle pasado desde MainActivity
        val name = arguments?.getString("NAME_BUNDLE")
        val address = arguments?.getString("ADDRESS_BUNDLE")

        // Por ejemplo, podrías mostrar estos valores en un TextView
        view.findViewById<TextView>(R.id.textViewName).text = name
        view.findViewById<TextView>(R.id.textViewAddress).text = address

        return view
    }

    companion object {

        const val NAME_BUNDLE = "name_bundle"
        const val ADDRESS_BUNDLE = "address_bundle"

        @JvmStatic
        fun newInstance(name: String, address: String) =
            BlankFragment().apply {
                arguments = Bundle().apply {
                    putString(NAME_BUNDLE, name)
                    putString(ADDRESS_BUNDLE, address)
                }
            }
    }
}
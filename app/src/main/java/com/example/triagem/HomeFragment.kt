package com.example.triagem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getClickListeners()


    }

    private fun getClickListeners() {
        view?.findViewById<CardView>(R.id.card_register)?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_registerFragment)
        }

        view?.findViewById<CardView>(R.id.card_attendance)?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_checkFragment)
        }
    }

}
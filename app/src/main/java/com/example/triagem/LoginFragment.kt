package com.example.triagem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment() {
    lateinit var user: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user = view.findViewById(R.id.user_edit)

        view.findViewById<Button>(R.id.login).setOnClickListener {
            val directions = LoginFragmentDirections.actionLoginFragmentToHomeFragment(user.text.toString())

            findNavController().navigate(directions)
        }
    }

}
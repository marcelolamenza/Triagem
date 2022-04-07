package com.example.triagem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.triagem.util.EditTextMask

class LoginFragment : Fragment() {
    lateinit var userEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userEditText = view.findViewById(R.id.user_edit)
        userEditText.addTextChangedListener(EditTextMask.mask("###.###.###-##", userEditText))

        view.findViewById<Button>(R.id.login).setOnClickListener {
            val directions =
                LoginFragmentDirections.actionLoginFragmentToHomeFragment(userEditText.text.toString())

            findNavController().navigate(directions)
        }
    }

}
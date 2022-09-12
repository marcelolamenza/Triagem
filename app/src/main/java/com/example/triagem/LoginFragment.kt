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
    lateinit var userName: EditText
    lateinit var password: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userName = view.findViewById(R.id.user_edit)
        userName.addTextChangedListener(EditTextMask.mask("###.###.###-##", userName))

        password = view.findViewById(R.id.editPassword)

        view.findViewById<Button>(R.id.login).setOnClickListener {
            val directions =
                LoginFragmentDirections.actionLoginFragmentToHomeFragment(userName.text.toString())

            findNavController().navigate(directions)
        }

        view.findViewById<Button>(R.id.new_user).setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

}
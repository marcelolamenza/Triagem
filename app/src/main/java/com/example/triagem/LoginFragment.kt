package com.example.triagem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.triagem.models.UserInfo
import com.example.triagem.util.*

class LoginFragment : Fragment(), FirebaseCallback {
    private lateinit var userName: EditText
    private lateinit var password: EditText
    private lateinit var loadingGif: ImageView

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
        loadingGif = view.findViewById(R.id.loading_gif)

        view.findViewById<Button>(R.id.login).setOnClickListener {
            val id = userName.text.toString()
            if(id.isNullOrEmpty()) {
                CustomToast.show(requireActivity(), "Insira um usuario!", CustomToast.GRAVITY_BOTTOM)
            } else {
                verifyPassword(id)
            }
        }

        view.findViewById<Button>(R.id.new_user).setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun verifyPassword(id: String) {
        loadAnimationSetup(true)

        val firebase = FirebaseHandler(this)
        firebase.retrieveUserData(id)
    }

    private fun loadAnimationSetup(isStarting: Boolean) {
        if (isStarting) {
            loadingGif.visibility = View.VISIBLE
            Glide.with(this).load(R.drawable.loading_gif).into(loadingGif)
        } else {
            loadingGif.visibility = View.GONE
        }
    }

    override fun fillLayoutWithUserInfo(userFinal: UserInfo) {
        loadAnimationSetup(false)

        if (userFinal.id != Constants.User.NO_USER) {
            if (userFinal.infoMap!![Constants.User.PASSWORD] == password.text.toString()) {
                val directions =
                    LoginFragmentDirections.actionLoginFragmentToHomeFragment(userName.text.toString())

                findNavController().navigate(directions)
            } else {
                CustomToast.show(requireActivity(), "Senha incorreta!", 0)
            }
        } else {
            CustomToast.show(requireActivity(), "404 Erro de Banco de dados!", 0)
        }
    }
}
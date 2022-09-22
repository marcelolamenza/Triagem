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
            val filledPassword = password.text.toString()

            when {
                id.isNullOrEmpty() -> {
                    CustomToast.showBottom(requireActivity(), "Insira um usuario!")
                }
                filledPassword.isNullOrEmpty() -> {
                    CustomToast.showBottom(requireActivity(), "Insira uma senha!")
                }
                else -> {
                    verifyPassword(id)
                }
            }
        }

        view.findViewById<Button>(R.id.new_user).setOnClickListener {
//            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            val directions =
                LoginFragmentDirections.actionLoginFragmentToRegisterFragment(null)
            findNavController().navigate(directions)
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
            Glide.with(this).load(R.drawable.loading_purple).into(loadingGif)
        } else {
            loadingGif.visibility = View.GONE
        }
    }

    override fun onDatabaseResponse(userFinal: UserInfo) {
        loadAnimationSetup(false)

        when {
            userFinal.infoMap.isNullOrEmpty() -> {
                CustomToast.showBottom(requireActivity(), "Usuário incorreto!!")
            }
            userFinal.infoMap!![Constants.User.PASSWORD] == password.text.toString() -> {
                val directions =
                    LoginFragmentDirections.actionLoginFragmentToHomeFragment(userName.text.toString())
                findNavController().navigate(directions)
            }
            else -> {
                CustomToast.showBottom(requireActivity(), "Senha incorreta!")
            }
        }
    }
}
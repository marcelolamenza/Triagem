package com.example.triagem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.triagem.models.UserInfo
import com.example.triagem.util.*

class LoginFragment : Fragment(), FirebaseCallback {
    private lateinit var userName: EditText
    private lateinit var password: EditText
    private lateinit var loadingGif: ImageView
    private lateinit var sharedPref: SharedPrefHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPrefHandler(requireActivity())
        userName = view.findViewById(R.id.user_edit)
        userName.addTextChangedListener(EditTextMask.apply("###.###.###-##", userName))
        password = view.findViewById(R.id.editPassword)
        loadingGif = view.findViewById(R.id.loading_gif)

        if (sharedPref.getString(Constants.SharedPref.FIRST_LOGIN) == Constants.SharedPref.DEFAULT_VALUE) {
            showWelcomeDialogs()
        }

        resetConfig()

        view.findViewById<Button>(R.id.login).setOnClickListener {
            val id = userName.text.toString()
            val filledPassword = password.text.toString()

            when {
                id.isEmpty() -> {
                    CustomToast.showBottom(requireActivity(), "Insira um usuario!")
                }
                filledPassword.isEmpty() -> {
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

    private fun resetConfig() {
        sharedPref.saveBoolean(Constants.SharedPref.SERVICE_ONGOING, false)
    }

    private fun showWelcomeDialogs() {
        createAlertDialog(
            "Essa é uma versão de testes e podem ocorrer alguns bug, qualquer erro " +
                    "encontrado podem informar dentro do Forms! Obrigado desde já!",
            "Fechar"
        ).show()

        createAlertDialog(
            "O processo de testes pode durar em média 15 minutos, incluindo criação de conta." +
                    "Todos os seus dados podem ser ficticios. Atenção que o valor de CPF será o seu login," +
                    " então grave qual número você inserir durante a criação de conta.",
            "Próximo"
        ).show()

        createAlertDialog(
            "Este aplicativo tem o intuito de facilitar a entrada de pacientes em " +
                    "hospitais, facilitando o processo de triagem e trazendo informações sobre os o ambiente físico de cada um deles!",
            "Próximo"
        ).show()

        sharedPref.saveString(Constants.SharedPref.FIRST_LOGIN, "true")
    }

    private fun createAlertDialog(message: String, nextButtonText: String): AlertDialog.Builder {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Sejam bem vindos!")
        builder.setMessage(message)
        builder.setPositiveButton(nextButtonText) { _, _ -> }

        return builder
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
                sharedPref.saveString(Constants.SharedPref.LOGIN, userName.text.toString())
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
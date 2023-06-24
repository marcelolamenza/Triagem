package com.example.triagem.fragments.wait

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.triagem.LoginFragmentDirections
import com.example.triagem.R
import com.example.triagem.models.UserInfo
import com.example.triagem.util.*
import kotlin.math.log

class WaitFragment : Fragment(), FirebaseCallback {
    private lateinit var sharedPref: SharedPrefHandler
    private lateinit var peopleText: TextView
    private lateinit var checkWaiting: CheckWaitTime
    private var peopleLeft = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            handleBackPress()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wait, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = SharedPrefHandler(requireActivity())

//        checkWaiting = MockedWaitTime()
//        peopleText = view.findViewById(R.id.waiting_users)
//        val pref =
//            requireActivity().getSharedPreferences(Constants.SharedPref.NAME, Context.MODE_PRIVATE)
//        val id = pref.getString(Constants.User.CPF, "")
//        val firebase = FirebaseHandler()
//        id?.let { firebase.retrieveUserData(it) }
        //findDatabase() //todo pegar o banco e carregar ID aqui, depois usar o dados dele para mostrar na tela
        //todo pegar o nivel de perigo e adicionar o tempo certo


        val currentDisease = sharedPref.getString(Constants.User.TRIAGE_DISEASE)
        val currentColor = sharedPref.getString(Constants.User.TRIAGE_COLOR)
        sharedPref.saveBoolean(Constants.SharedPref.SERVICE_ONGOING, true)

        val textCurrentDisease = view.findViewById<TextView>(R.id.description)
        textCurrentDisease.text = currentDisease
        textCurrentDisease.setTextColor(getDiseaseColor(currentColor!!))

        view.findViewById<Button>(R.id.cancel_button).setOnClickListener {
            handleBackPress()
        }
    }

    private fun getDiseaseColor(color: String) : Int{
        return when (color) {
            PatientState.RED.toString() -> ContextCompat.getColor(requireContext(), R.color.red)
            PatientState.ORANGE.toString() -> ContextCompat.getColor(requireContext(), R.color.orange)
            PatientState.YELLOW.toString() -> ContextCompat.getColor(requireContext(), R.color.yellow)
            PatientState.GREEN.toString() -> ContextCompat.getColor(requireContext(), R.color.green)
            else -> ContextCompat.getColor(requireContext(), R.color.blue)
        }
    }

    private fun startUpdate() {
        object : CountDownTimer(10000, 2000) {

            // Callback function, triggered on regular interval
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                peopleLeft = checkWaiting.retrievePeopleLeft()

                peopleText.text = "$peopleLeft "
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {

                if (peopleLeft == 0) {
                    peopleText.text = "DONE"
                } else {
                    start()
                }
            }
        }.start()
    }

    private fun handleBackPress() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Você deseja cancelar a espera?")
        builder.setPositiveButton("Sim") { _, _ ->
            sharedPref.saveBoolean(Constants.SharedPref.SERVICE_ONGOING, false)
            returnToHomeScreen()
        }
        builder.setNegativeButton("Não") { _, _ -> }

        builder.show()
    }

    private fun returnToHomeScreen() {
        val login = sharedPref.getString(Constants.SharedPref.LOGIN)
        val directions = WaitFragmentDirections.actionWaitFragmentToHomeFragment(login!!)
        findNavController().navigate(directions)
    }

    override fun onDatabaseResponse(userFinal: UserInfo) {
        startUpdate()
    }
}
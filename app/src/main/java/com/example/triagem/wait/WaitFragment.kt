package com.example.triagem.wait

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.triagem.R
import com.example.triagem.models.UserInfo
import com.example.triagem.util.Constants
import com.example.triagem.util.FirebaseCallback
import com.example.triagem.util.FirebaseHandler

class WaitFragment : Fragment(), FirebaseCallback {
    lateinit var peopleText: TextView
    lateinit var checkWaiting: CheckWaitTime
    var peopleLeft = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wait, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkWaiting = MockedWaitTime()
        peopleText = view.findViewById(R.id.waiting_users)

        val pref = requireActivity().getSharedPreferences(Constants.SharedPref.NAME, Context.MODE_PRIVATE)
        val id = pref.getString(Constants.User.CPF, "")

        val firebase = FirebaseHandler()
        id?.let { firebase.retrieveUserData(it) }

        //findDatabase() //todo pegar o banco e carregar ID aqui, depois usar o dados dele para mostrar na tela
        //todo pegar o nivel de perigo e adicionar o tempo certo



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

    override fun onDatabaseResponse(userFinal: UserInfo) {


        startUpdate()
    }
}
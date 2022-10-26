package com.example.triagem.wait

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.triagem.R

class WaitFragment : Fragment() {
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

        object : CountDownTimer(10000, 2000) {

            // Callback function, fired on regular interval
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                peopleLeft = checkWaiting.retrievePeopleLeft()

                peopleText.text = "$peopleLeft "
            }

            // Callback function, fired
            // when the time is up
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
}
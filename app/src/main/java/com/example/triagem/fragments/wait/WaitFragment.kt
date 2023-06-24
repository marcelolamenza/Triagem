package com.example.triagem.fragments.wait

import android.annotation.SuppressLint
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
import com.example.triagem.R
import com.example.triagem.models.HospitalInfo
import com.example.triagem.util.*

class WaitFragment : Fragment() {
    private lateinit var sharedPref: SharedPrefHandler

    private var contador: CountDownTimer? = null

    private var currentNumber = 0
    private var currentColor = ""

    lateinit var peopleLeftText: TextView
    lateinit var waitingTimeText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            returnToHomeScreen()
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
        peopleLeftText = view.findViewById(R.id.waiting_users)
        waitingTimeText = view.findViewById(R.id.waiting_time)

        sharedPref = SharedPrefHandler(requireActivity())
        val hospitalInfo = sharedPref.getDataClass<HospitalInfo>(Constants.SharedPref.CURRENT_HOSPITAL)

        currentColor = sharedPref.getString(Constants.User.TRIAGE_COLOR)!!

        if (!sharedPref.getBoolean(Constants.SharedPref.SERVICE_ONGOING)) {
            sharedPref.saveBoolean(Constants.SharedPref.SERVICE_ONGOING, true)
            currentNumber = getTotalWaitingNumber(hospitalInfo!!, currentColor)

        } else {
            currentNumber = sharedPref.getInt(Constants.SharedPref.CURRENT_NUMBER)
        }

        peopleLeftText.text = currentNumber.toString()
        waitingTimeText.text = getCurrentTime()
        setCurrentDisease(view)

        view.findViewById<Button>(R.id.cancel_button).setOnClickListener {
            handleBackPress()
        }

        if (contador == null) {
            startUpdate()
        }
    }

    private fun getCurrentTime(): String {
        val currentTime = calculateTotalTime(currentNumber, currentColor)
        return currentTime.toString()
    }

    private fun setCurrentDisease(view: View) {
        val currentDisease = sharedPref.getString(Constants.User.TRIAGE_DISEASE)

        val textCurrentDisease = view.findViewById<TextView>(R.id.description)
        textCurrentDisease.text = currentDisease
        textCurrentDisease.setTextColor(getDiseaseColor(currentColor))
    }


    private fun getTotalWaitingNumber(hospitalInfo: HospitalInfo, currentColor: String): Int {
        val population = hospitalInfo.population

        val result = when (currentColor) {
            PatientState.RED.toString() -> population.red
            PatientState.ORANGE.toString() -> population.red + population.orange
            PatientState.YELLOW.toString() -> population.red + population.orange + population.yellow
            PatientState.GREEN.toString() -> population.red + population.orange + population.yellow + population.green
            else -> population.red + population.orange + population.yellow + population.green + population.blue
        }

        return result + 1
    }

    private fun calculateTotalTime(currentNumber: Int, currentColor: String): Int {
        val result: Int = when (currentColor) {
            PatientState.RED.toString() -> 0
            PatientState.ORANGE.toString() -> currentNumber * 1.coerceAtMost(10)
            PatientState.YELLOW.toString() -> currentNumber * 2.coerceAtMost(30)
            PatientState.GREEN.toString() -> currentNumber * 3.coerceAtMost(60)
            else -> currentNumber * 4.coerceAtMost(120)
        }

        return result
    }

    private fun getDiseaseColor(color: String): Int {
        return when (color) {
            PatientState.RED.toString() -> ContextCompat.getColor(requireContext(), R.color.red)
            PatientState.ORANGE.toString() -> ContextCompat.getColor(
                requireContext(),
                R.color.orange
            )
            PatientState.YELLOW.toString() -> ContextCompat.getColor(
                requireContext(),
                R.color.yellow
            )
            PatientState.GREEN.toString() -> ContextCompat.getColor(requireContext(), R.color.green)
            else -> ContextCompat.getColor(requireContext(), R.color.blue)
        }
    }

    private fun startUpdate() {
        val totalTime = calculateTotalTime(currentNumber, currentColor)
        contador = object : CountDownTimer((totalTime * 1000).toLong(), 1000) {

            override fun onTick(millisUntilFinished: Long) {
                if (currentNumber <= 0) {
                    currentNumber = 0
                    peopleLeftText.text = currentNumber.toString()
                    waitingTimeText.text = getCurrentTime()

                    cancel()
                } else {
                    currentNumber -= 1
                    peopleLeftText.text = currentNumber.toString()
                    waitingTimeText.text = getCurrentTime()

                    sharedPref.saveInt(Constants.SharedPref.CURRENT_NUMBER, currentNumber)
                }
            }

            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                sharedPref.saveBoolean(Constants.SharedPref.SERVICE_ONGOING, false)

                peopleLeftText.text = "Espera finalizada"

                val builder = AlertDialog.Builder(requireContext())
                builder.setTitle("Sua vez chegou!")
                builder.setMessage("Por favor se encaminhar a um atendente para prosseguir com seu tratamento.")
                builder.setPositiveButton("Confirmar") { _, _ -> }
                builder.show()
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
}
package com.example.triagem.fragments.diagnosis

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.triagem.R
import com.example.triagem.adapters.DiagnosisItemAdapter
import com.example.triagem.util.Constants
import com.example.triagem.util.Diseases
import com.example.triagem.util.PatientState
import com.example.triagem.util.SharedPrefHandler
import kotlinx.android.synthetic.main.fragment_register.recycler_view

class DiagnosisFragment : Fragment(), DiagnosisCallback {
    private val adapter by lazy { DiagnosisItemAdapter(this) }

    private var currentState = PatientState.FIRST_SLOT
    private var numberOfOptionsClicked = 0
    private var currentDiseaseListIndex = 0
    private var hasMoreOptions = false

    private lateinit var chooseNextOptionsButton: Button
    private lateinit var confirmOptionButton: Button
    private lateinit var sharedPref: SharedPrefHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_check, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = SharedPrefHandler(requireActivity())

        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(context)

        fillRecycler()

        chooseNextOptionsButton = view.findViewById(R.id.incorrect_choice_button)
        chooseNextOptionsButton.setOnClickListener {
            fillRecycler()
        }

        confirmOptionButton = view.findViewById(R.id.confirmation_button)
        confirmOptionButton.setOnClickListener {
            solveTriage()
        }
        enableButton(confirmOptionButton, false)
    }

    private fun solveTriage() {
        sharedPref.saveString(Constants.User.TRIAGE_COLOR, currentState.toString())

        findNavController().navigate(R.id.action_checkFragment_to_mapsFragment)
    }

    private fun fillRecycler() {
        adapter.clean()

        if(!hasMoreOptions) {
            currentState = currentState.next()!!
        }

        val diseases = when (currentState) {
            PatientState.RED -> Diseases.redDiseases
            PatientState.ORANGE -> Diseases.orangeDiseases
            PatientState.YELLOW -> Diseases.yellowDiseases
            PatientState.GREEN -> Diseases.greenDiseases
            PatientState.BLUE -> Diseases.blueDiseases
            else -> emptyList()
        }

        setOptionsToCurrentState(diseases)
    }

    private fun setOptionsToCurrentState(items: List<String>) {
        var maxSize = currentDiseaseListIndex + 4

        if (maxSize >= items.size) {
            maxSize = items.size
            hasMoreOptions = false
        } else {
            hasMoreOptions = true
        }

        val subList = items.subList(currentDiseaseListIndex, maxSize)

        currentDiseaseListIndex = if (hasMoreOptions) {
            maxSize
        } else {
            0
        }

        adapter.addMultipleItems(subList)
    }

    override fun clickAction(button: Button, isSelected: Boolean, position: Int) {
        val selectedButtonColor: ColorStateList
        val selectedButtonTextColor: ColorStateList

        if (isSelected) {
            numberOfOptionsClicked++
            selectedButtonColor = retrieveColorResource(R.color.colorPrimaryDark)
            selectedButtonTextColor = retrieveColorResource(R.color.white)

            sharedPref.saveString(Constants.User.TRIAGE_DISEASE, button.text.toString())

        } else {
            numberOfOptionsClicked--
            selectedButtonColor = retrieveColorResource(R.color.colorPrimary)
            selectedButtonTextColor = retrieveColorResource(R.color.black)
        }

        button.backgroundTintList = selectedButtonColor
        button.setTextColor(selectedButtonTextColor)

        adapter.addTransparencyToOtherOptions(
            position,
            isSelected,
            retrieveColorResource(R.color.colorPrimary),
            retrieveColorResource(R.color.black)
        )

        if (numberOfOptionsClicked == 0) {
            enableButton(chooseNextOptionsButton, true)
            enableButton(confirmOptionButton, false)
        } else {
            numberOfOptionsClicked = 1

            enableButton(chooseNextOptionsButton, false)
            enableButton(confirmOptionButton, true)

        }
    }

    private fun retrieveColorResource(color: Int): ColorStateList {
        return ColorStateList.valueOf(resources.getColor(color))
    }

    private fun enableButton(button: Button, enable: Boolean) {
        if (enable) {
            button.alpha = 1f
            button.isClickable = true
        } else {
            button.alpha = 0.5f
            button.isClickable = false
        }
    }
}
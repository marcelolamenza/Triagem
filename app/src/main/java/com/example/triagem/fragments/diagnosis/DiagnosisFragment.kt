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
    private lateinit var actionButton: Button

    private var numberOfOptionsClicked = 0
    private var currentDiseaseListIndex = 0
    private var hasMoreOptions = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_check, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(context)

        fillRecycler()

        actionButton = view.findViewById(R.id.button)
        actionButton.setOnClickListener {
            checkNextAction()
        }
    }

    private fun checkNextAction() {
        if (numberOfOptionsClicked != 0) {
            solveTriage()
        } else {
            fillRecycler()
        }
    }

    private fun solveTriage() {
        val sharedPref = SharedPrefHandler(requireActivity())
        sharedPref.saveString(Constants.User.TRIAGE_RESULT, currentState.toString())

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

    private fun blockNextButton() {
//        actionButton.
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


        // todo Testar essa otimização
//        val maxSize = currentDiseaseListIndex + 4
//        val subList = items.subList(currentDiseaseListIndex, maxSize.coerceAtMost(items.size))
//        hasMoreOptions = maxSize < items.size
//        currentDiseaseListIndex = if (hasMoreOptions) maxSize else 0
//        adapter.addMultipleItems(subList)
    }

    override fun clickAction(button: Button, isSelected: Boolean, position: Int) {
        val selectedButtonColor: ColorStateList
        val selectedButtonTextColor: ColorStateList

        if (isSelected) {
            numberOfOptionsClicked++
            selectedButtonColor = retrieveColorResource(R.color.colorPrimaryDark)
            selectedButtonTextColor = retrieveColorResource(R.color.white)
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
            actionButton.text = getString(R.string.action_button_no_option_selected)
        } else {
            numberOfOptionsClicked = 1
            actionButton.text = getString(R.string.action_button_option_selested)
        }
    }

    private fun retrieveColorResource(color: Int): ColorStateList {
        return ColorStateList.valueOf(resources.getColor(color))
    }
}
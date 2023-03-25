package com.example.triagem.diagnosis

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
import com.example.triagem.util.Diseases
import com.example.triagem.util.PatientState
import kotlinx.android.synthetic.main.fragment_register.recycler_view


class DiagnosisFragment : Fragment(), DiagnosisCallback {
    private val adapter by lazy { DiagnosisItemAdapter(this) }
    private var currentState = PatientState.FIRST_SLOT
    private var numberOfOptionsClicked = 0
    private lateinit var actionButton: Button
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
            solveTriagem()
        } else {
            fillRecycler()
        }
    }

    private fun solveTriagem() {
        findNavController().navigate(R.id.action_checkFragment_to_mapsFragment)
    }

    private fun fillRecycler() {
        adapter.clean()

        if(!hasMoreOptions) {
            currentState = currentState.next()!!
        }

        when (currentState) {
            PatientState.RED -> {
                setOptionsToCurrentState(Diseases.redDiseases)
            }
            PatientState.ORANGE -> {
                setOptionsToCurrentState(Diseases.orangeDiseases)
            }
            PatientState.YELLOW -> {
                setOptionsToCurrentState(Diseases.yellowDiseases)
            }
            PatientState.GREEN -> {
                setOptionsToCurrentState(Diseases.greenDiseases)
            }
            PatientState.BLUE -> {
                setOptionsToCurrentState(Diseases.blueDiseases)
            }
            else -> {
                blockNextButton()
            }
        }
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

    }

    override fun clickAction(button: Button, isSelected: Boolean, position: Int) {
        val selectedButtonColor: ColorStateList
        val selectedButtonTextColor: ColorStateList

        val neutralButtonTextColor = ColorStateList.valueOf(resources.getColor(R.color.black))
        val neutralButtonColor = ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))

        if (isSelected) {
            numberOfOptionsClicked++
            selectedButtonColor = ColorStateList.valueOf(resources.getColor(R.color.colorPrimaryDark))
            selectedButtonTextColor = ColorStateList.valueOf(resources.getColor(R.color.white))
        } else {
            numberOfOptionsClicked--
            selectedButtonColor = ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
            selectedButtonTextColor = ColorStateList.valueOf(resources.getColor(R.color.black))
        }

        button.backgroundTintList = selectedButtonColor
        button.setTextColor(selectedButtonTextColor)

        adapter.addTransparencyToOtherOptions(
            position,
            isSelected,
            neutralButtonColor,
            neutralButtonTextColor
        )

        if (numberOfOptionsClicked == 0) {
            actionButton.text = getString(R.string.action_button_no_option_selected)
        } else {
            numberOfOptionsClicked = 1
            actionButton.text = getString(R.string.action_button_option_selested)
        }
    }
}
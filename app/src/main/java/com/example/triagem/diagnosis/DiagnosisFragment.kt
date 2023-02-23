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
    private var state = PatientState.FIRST_SLOT
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
            state = state.next()!!
        }

        when (state) {
            PatientState.RED -> {
                refillSameColor(Diseases.redDiseases)
            }
            PatientState.ORANGE -> {
                refillSameColor(Diseases.orangeDiseases)
            }
            PatientState.YELLOW -> {
                refillSameColor(Diseases.yellowDiseases)
            }
            PatientState.GREEN -> {
                refillSameColor(Diseases.greenDiseases)
            }
            PatientState.BLUE -> {
                refillSameColor(Diseases.blueDiseases)
            }
            else -> {
            }
        }
    }

    private fun refillSameColor(items: List<String>) {
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
        val buttonColor: ColorStateList
        val buttonTextColor: ColorStateList

        val neutralTextColor = ColorStateList.valueOf(resources.getColor(R.color.black))
        val neutralBackgroundColor = ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))

        if (isSelected) {
            numberOfOptionsClicked++
            buttonColor = ColorStateList.valueOf(resources.getColor(R.color.colorPrimaryDark))
            buttonTextColor = ColorStateList.valueOf(resources.getColor(R.color.white))
        } else {
            numberOfOptionsClicked--
            buttonColor = ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
            buttonTextColor = ColorStateList.valueOf(resources.getColor(R.color.black))
        }

        button.backgroundTintList = buttonColor
        button.setTextColor(buttonTextColor)

        adapter.addTransparencyToOtherOptions(
            position,
            isSelected,
            neutralBackgroundColor,
            neutralTextColor
        )

        if (numberOfOptionsClicked == 0) {
            actionButton.text = getString(R.string.action_button_no_option_selected)
        } else {
            actionButton.text = getString(R.string.action_button_option_selested)
        }
    }
}
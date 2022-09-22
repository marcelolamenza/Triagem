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
import com.example.triagem.util.PatientState
import kotlinx.android.synthetic.main.fragment_register.recycler_view


class DiagnosisFragment : Fragment(), DiagnosisCallback {
    private val adapter by lazy { DiagnosisItemAdapter(this) }
    private var state = PatientState.FIRST_SLOT
    private var numberOfOptionsClicked = 0
    private lateinit var actionButton: Button

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

        state = state.next()!!

        when (state) {
            PatientState.RED -> {
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("ACIDENTES AUTOMOBILISTICO"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("TRAUMATISMO"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("VITIMA DE ARMA DE FOGO"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("INSUFICIENCIA RESPIRATÓRIA"))
            }
            PatientState.ORANGE -> {
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("INFARTO"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("HEMORRAGIA"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("FRATURA"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("PERDA DE CONSCIENCIA"))
            }
            PatientState.YELLOW -> {
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("DESIDRATAÇÃO"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("INFECÇÃO"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("HEMORRAGIA(LEVE)"))
            }
            PatientState.GREEN -> {
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("DOR DE GARGANTA"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("FEBRE"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("TOSSE"))

            }
            PatientState.BLUE -> {

            }
            else -> {
            }
        }
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
package com.example.triagem.diagnosis

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.triagem.R
import com.example.triagem.adapters.DiagnosisItemAdapter
import com.example.triagem.util.DiagnosisCallback
import com.example.triagem.util.PacientState
import kotlinx.android.synthetic.main.fragment_register.recycler_view


class DiagnosisFragment : Fragment(), DiagnosisCallback {
    private val adapter by lazy { DiagnosisItemAdapter(this) }
    private var state = PacientState.FIRST_SLOT
    private var numberOfOptionsClicked = 0
    private lateinit var actionButton: Button

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
        TODO("Not yet implemented")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check, container, false)
    }

    private fun fillRecycler() {
        adapter.clean()

        state = state.next()!!

        when (state) {
            PacientState.RED -> {
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("ACIDENTES AUTOMOBILISTICO"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("TRAUMATISMO"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("VITIMA DE ARMA DE FOGO"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("INSUFICIENCIA RESPIRATÓRIA"))
            }
            PacientState.ORANGE -> {
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("INFARTO"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("HEMORRAGIA"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("FRATURA"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("PERDA DE CONSCIENCIA"))
            }
            PacientState.YELLOW -> {
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("DESIDRATAÇÃO"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("INFECÇÃO"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("HEMORRAGIA(LEVE)"))
            }
            PacientState.GREEN -> {
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("DOR DE GARGANTA"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("FEBRE"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("TOSSE"))

            }
            PacientState.BLUE -> {

            }
            else -> {
            }
        }

    }

    override fun clickAction(button: Button, isClicked: Boolean) {
        val buttonColor: ColorStateList
        val buttonTextColor: ColorStateList

        if (isClicked) {
            numberOfOptionsClicked++
            buttonColor = ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
            buttonTextColor = ColorStateList.valueOf(resources.getColor(R.color.white))
        } else {
            numberOfOptionsClicked--
            buttonColor = ColorStateList.valueOf(resources.getColor(R.color.colorAccent))
            buttonTextColor = ColorStateList.valueOf(resources.getColor(R.color.black))
        }

        button.backgroundTintList = buttonColor
        button.setTextColor(buttonTextColor)

        if (numberOfOptionsClicked == 0) {
            actionButton.text = getString(R.string.action_button_no_option_selected)
        } else {
            actionButton.text = getString(R.string.action_button_option_selested)
        }
    }
}
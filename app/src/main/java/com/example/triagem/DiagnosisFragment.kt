package com.example.triagem

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.triagem.adapters.DiagnosisItemAdapter
import com.example.triagem.util.DiagnosisCallback
import kotlinx.android.synthetic.main.fragment_register.recycler_view


class DiagnosisFragment : Fragment(), DiagnosisCallback {
    private val adapter by lazy {DiagnosisItemAdapter(this) }
    private var state = State.RED

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(context)

//        adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("INFARTO"))
//        adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("DOR NO PEITO"))
//        adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("SOFRIMENTO"))
//        adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("FUNCIONA"))
        fillRecycler()

        val button : Button = view.findViewById(R.id.button)

        button.setOnClickListener {
            fillRecycler()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check, container, false)
    }

    private fun fillRecycler(){
        adapter.clean()
        when(state){
            State.RED -> {
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("ACIDENTES AUTOMOBILISTICO"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("TRAUMATISMO"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("VITIMA DE ARMA DE FOGO"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("INSUFICIENCIA RESPIRATÓRIA"))
            }
            State.ORANGE -> {
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("INFARTO"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("HEMORRAGIA"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("FRATURA"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("PERDA DE CONSCIENCIA"))
            }
            State.YELLOW -> {
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("DESIDRATAÇÃO"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("INFECÇÃO"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("HEMORRAGIA(LEVE)"))

            }
            State.GREEN -> {
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("DOR DE GARGANTA"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("FEBRE"))
                adapter.addItem(DiagnosisItemAdapter.DiagnosisItem("TOSSE"))

            }
            State.BLUE -> {

            }
            State.EMPTY -> {

            }
        }

        state = state.next()!!
    }



    override fun nextFrag(button: Button, isClicked: Boolean){
        if(isClicked){
            button.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorPrimary))
        }else{
            button.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.colorAccent))
        }
    }
//
//    fun clear() {
//        val size: Int = adapter.
//        if (size > 0) {
//            for (i in 0 until size) {
//                android.R.attr.data.remove(0)
//            }
//            notifyItemRangeRemoved(0, size)
//        }
//    }

}

enum class State{
    RED, ORANGE, YELLOW, GREEN, BLUE, EMPTY
    {
        override fun next(): State? {
            return null // see below for options for this line
        }
    };

    open operator fun next(): State? {
        // No bounds checking required here, because the last instance overrides
        return values()[ordinal + 1]
    }
}
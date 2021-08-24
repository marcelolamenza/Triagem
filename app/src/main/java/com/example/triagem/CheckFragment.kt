package com.example.triagem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.triagem.adapters.CheckItemAdapter
import com.example.triagem.util.AdapterInterface
import kotlinx.android.synthetic.main.fragment_check.*
import kotlinx.android.synthetic.main.fragment_register.recycler_view


class CheckFragment : Fragment(), AdapterInterface.AdapterCallback {
    private val adapter by lazy {CheckItemAdapter() }
    private var state = State.RED

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(context)

        adapter.setCallback(this)
//        adapter.addItem(CheckItemAdapter.CheckItem("INFARTO"))
//        adapter.addItem(CheckItemAdapter.CheckItem("DOR NO PEITO"))
//        adapter.addItem(CheckItemAdapter.CheckItem("SOFRIMENTO"))
//        adapter.addItem(CheckItemAdapter.CheckItem("FUNCIONA"))
        fillRecycler()

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

    fun fillRecycler(){
        adapter.clean()
        when(state){
            State.RED -> {
                adapter.addItem(CheckItemAdapter.CheckItem("ACIDENTES AUTOMOBILISTICO"))
                adapter.addItem(CheckItemAdapter.CheckItem("TRAUMATISMO"))
                adapter.addItem(CheckItemAdapter.CheckItem("VITIMA DE ARMA DE FOGO"))
                adapter.addItem(CheckItemAdapter.CheckItem("INSUFICIENCIA RESPIRATÓRIA"))
            }
            State.ORANGE -> {
                adapter.addItem(CheckItemAdapter.CheckItem("INFARTO"))
                adapter.addItem(CheckItemAdapter.CheckItem("HEMORRAGIA"))
                adapter.addItem(CheckItemAdapter.CheckItem("FRATURA"))
                adapter.addItem(CheckItemAdapter.CheckItem("PERDA DE CONSCIENCIA"))
            }
            State.YELLOW -> {
                adapter.addItem(CheckItemAdapter.CheckItem("DESIDRATAÇÃO"))
                adapter.addItem(CheckItemAdapter.CheckItem("INFECÇÃO"))
                adapter.addItem(CheckItemAdapter.CheckItem("HEMORRAGIA(LEVE)"))

            }
            State.GREEN -> {
                adapter.addItem(CheckItemAdapter.CheckItem("DOR DE GARGANTA"))
                adapter.addItem(CheckItemAdapter.CheckItem("FEBRE"))
                adapter.addItem(CheckItemAdapter.CheckItem("TOSSE"))

            }
            State.BLUE -> {

            }
            State.EMPTY -> {

            }
        }

        state = state.next()!!
    }

    override fun nextFrag() {
//        findNavController().navigate(R.id.)
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
    RED,ORANGE,YELLOW,BLUE,GREEN, EMPTY
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
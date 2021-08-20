package com.example.triagem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.triagem.adapters.CheckItemAdapter
import com.example.triagem.util.AdapterInterface
import kotlinx.android.synthetic.main.fragment_register.*


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
                adapter.addItem(CheckItemAdapter.CheckItem("INFARTO"))
                adapter.addItem(CheckItemAdapter.CheckItem("DOR NO PEITO"))
                adapter.addItem(CheckItemAdapter.CheckItem("SOFRIMENTO"))
                adapter.addItem(CheckItemAdapter.CheckItem("FUNCIONA"))
            }
            State.ORANGE -> {
                adapter.addItem(CheckItemAdapter.CheckItem("OPS"))
                adapter.addItem(CheckItemAdapter.CheckItem(" NPEITO"))
                adapter.addItem(CheckItemAdapter.CheckItem("HAHAHAHA"))
            }
            State.YELLOW -> {

            }
            State.GREEN -> {

            }
            State.BLUE -> {

            }
            State.EMPTY -> {

            }
        }

        state = state.next()!!
    }

    override fun nextFrag() {
        fillRecycler()
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
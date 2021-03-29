package com.example.triagem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.triagem.adapters.RegisterItemAdapter
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {
    private val adapter by lazy { RegisterItemAdapter() }
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(context)

        adapter.addItem(RegisterItemAdapter.RegisterItem("Nome"))
        adapter.addItem(RegisterItemAdapter.RegisterItem("Sobrenome"))
        adapter.addItem(RegisterItemAdapter.RegisterItem("telefone"))
        adapter.addItem(RegisterItemAdapter.RegisterItem("endereço"))
        adapter.addItem(RegisterItemAdapter.RegisterItem("e-mail"))
        adapter.addItem(RegisterItemAdapter.RegisterItem("RG"))
        adapter.addItem(RegisterItemAdapter.RegisterItem("CPF"))
        adapter.addItem(RegisterItemAdapter.RegisterItem("2"))
        adapter.addItem(RegisterItemAdapter.RegisterItem("3"))

        btn_register.setOnClickListener{

        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

}
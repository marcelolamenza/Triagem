package com.example.triagem.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.triagem.R
import com.example.triagem.adapters.RegisterItemAdapter
import com.example.triagem.models.UserInfo
import com.example.triagem.util.Constants
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment() {
    private val adapter by lazy { RegisterItemAdapter() }
    private lateinit var recyclerView: RecyclerView
    private var userId = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        fillRecyclerview()

        btn_register.setOnClickListener {
            val userHash = getDataFromRecyclerView()

            val userInfo = UserInfo(userId, userHash)
            val directions =
                RegisterFragmentDirections.actionRegisterFragmentToRegisterDetailsFragment(userInfo)

            findNavController().navigate(directions)
        }
    }

    private fun fillRecyclerview() {
        adapter.addItem(RegisterItemAdapter.RegisterItem(Constants.User.FIRST_NAME))
        adapter.addItem(RegisterItemAdapter.RegisterItem(Constants.User.LAST_NAME))
        adapter.addItem(RegisterItemAdapter.RegisterItem(Constants.User.PHONE))
        adapter.addItem(RegisterItemAdapter.RegisterItem(Constants.User.ADDRESS))
        adapter.addItem(RegisterItemAdapter.RegisterItem(Constants.User.EMAIL))
        adapter.addItem(RegisterItemAdapter.RegisterItem(Constants.User.RG))
        adapter.addItem(RegisterItemAdapter.RegisterItem(Constants.User.CPF))
    }

    private fun getDataFromRecyclerView(): HashMap<String, String> {
        val user = hashMapOf<String, String>()
        for (info in adapter.getItems()) {
            if (info.itemLabel == Constants.User.CPF) {
                userId = info.itemDescription!!
            }

            info.itemDescription?.let { user.put(info.itemLabel, it) }
        }

        return user
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }
}
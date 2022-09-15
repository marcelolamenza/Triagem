package com.example.triagem.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.triagem.R
import com.example.triagem.adapters.RegisterItemAdapter
import com.example.triagem.models.UserInfo
import com.example.triagem.util.Constants
import com.example.triagem.util.FirebaseCallback
import com.example.triagem.util.FirebaseHandler
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment(), FirebaseCallback {
    private val adapter by lazy { RegisterItemAdapter() }
    private lateinit var recyclerView: RecyclerView
    private var userId = ""
    private lateinit var loadingGif: ImageView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        loadingGif = view.findViewById(R.id.loading_gif)

        fillRecyclerview()

        btn_register.setOnClickListener {
            val userHashMap = getDataFromRecyclerView()

            val userInfo = UserInfo(userId, userHashMap)
            val directions =
                RegisterFragmentDirections.actionRegisterFragmentToRegisterDetailsFragment(userInfo)

            findNavController().navigate(directions)
        }
    }

    private fun loadUserInformation(id: String) {
        loadAnimationSetup(true)

        val firebase = FirebaseHandler(this)
        firebase.retrieveUserData(id)
    }

    private fun fillRecyclerview() {
        adapter.addItem(RegisterItemAdapter.RegisterItem(Constants.User.FIRST_NAME))
        adapter.addItem(RegisterItemAdapter.RegisterItem(Constants.User.LAST_NAME))
        adapter.addItem(RegisterItemAdapter.RegisterItem(Constants.User.RG))
        adapter.addItem(RegisterItemAdapter.RegisterItem(Constants.User.CPF))
        adapter.addItem(RegisterItemAdapter.RegisterItem(Constants.User.PHONE))
        adapter.addItem(RegisterItemAdapter.RegisterItem(Constants.User.ADDRESS))
        adapter.addItem(RegisterItemAdapter.RegisterItem(Constants.User.EMAIL))
        adapter.addItem(RegisterItemAdapter.RegisterItem(Constants.User.PASSWORD))

        if(arguments != null) {
            val bundle = arguments
            if (bundle == null) {
                Log.e(Constants.LogMessage.ERROR, "Didn't receive information from RegisterFragment")
            } else {
                val args = RegisterFragmentArgs.fromBundle(bundle)

                args.id?.let { loadUserInformation(it) }
            }
        }
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

    private fun loadAnimationSetup(shouldRun: Boolean) {
        if (shouldRun) {
            loadingGif.visibility = View.VISIBLE
            Glide.with(this).load(R.drawable.loading_gif).into(loadingGif)
        } else {
            loadingGif.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onDatabaseResponse(userFinal: UserInfo) {
        loadAnimationSetup(false)

        adapter.updateFieldInformation(userFinal)
    }
}
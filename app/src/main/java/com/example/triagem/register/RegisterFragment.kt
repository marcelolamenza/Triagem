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
import kotlin.collections.HashMap

class RegisterFragment : Fragment(), FirebaseCallback {
    private lateinit var recyclerView: RecyclerView
    private lateinit var loadingGif: ImageView
    private var isEditing = false
    private val adapter by lazy { RegisterItemAdapter() }
    private var userId = ""
    private var password = ""
    private var diseases = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

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
                RegisterFragmentDirections.actionRegisterFragmentToRegisterDetailsFragment(
                    userInfo,
                    isEditing
                )

            findNavController().navigate(directions)
        }
    }

    private fun fillRecyclerview() {
        adapter.addMultipleItems(
            listOf(
                Constants.User.FIRST_NAME,
                Constants.User.LAST_NAME,
                Constants.User.RG,
                Constants.User.PHONE,
                Constants.User.ADDRESS,
                Constants.User.EMAIL
            )
        )

        retrieveDataFromBundle()

        if (isEditing) {
            loadUserInformation(userId)
        } else {
            adapter.addItem(RegisterItemAdapter.RegisterItem(Constants.User.CPF))
            adapter.addItem(RegisterItemAdapter.RegisterItem(Constants.User.PASSWORD))
        }
    }

    private fun retrieveDataFromBundle() {
        if (arguments != null) {
            val bundle = arguments
            if (bundle == null) {
                Log.e(
                    Constants.LogMessage.ERROR,
                    "Didn't receive information from RegisterFragment"
                )
            } else {
                val args = RegisterFragmentArgs.fromBundle(bundle)

                if (args.id != null) {
                    userId = args.id!!
                    isEditing = args.isEditing
                }
            }
        }
    }

    private fun loadUserInformation(id: String) {
        loadAnimationSetup(true)

        val firebase = FirebaseHandler(this)
        firebase.retrieveUserData(id)
    }

    private fun getDataFromRecyclerView(): HashMap<String, String> {
        val user = hashMapOf<String, String>()
        for (info in adapter.getItems()) {
            if (info.itemLabel == Constants.User.CPF) {
                userId = info.itemDescription!!
            }

            info.itemDescription?.let { user.put(info.itemLabel, it) }
        }

        if (isEditing) {
            user[Constants.User.CPF] = userId
            user[Constants.User.PASSWORD] = password
            user[Constants.User.DISEASES] = diseases
        }

        return user
    }

    private fun loadAnimationSetup(isRunning: Boolean) {
        if (isRunning) {
            loadingGif.visibility = View.VISIBLE
            Glide.with(this).load(R.drawable.loading_purple).into(loadingGif)
        } else {
            loadingGif.visibility = View.GONE
        }
    }

    override fun onDatabaseResponse(userFinal: UserInfo) {
        loadAnimationSetup(false)
        adapter.updateFieldInformation(userFinal)
        password = userFinal.infoMap?.get(Constants.User.PASSWORD) ?: ""
        diseases = userFinal.infoMap?.get(Constants.User.DISEASES) ?: Constants.LogMessage.DEV_ERROR
    }
}
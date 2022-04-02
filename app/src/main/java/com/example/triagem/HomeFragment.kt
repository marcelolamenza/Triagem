package com.example.triagem

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.triagem.models.UserInfo
import com.example.triagem.util.Constants
import com.example.triagem.util.FirebaseCallback
import com.example.triagem.util.FirebaseHandler

class HomeFragment : Fragment(), FirebaseCallback {

    private lateinit var userCard: ConstraintLayout
    private lateinit var name: TextView
    private lateinit var bloodType: TextView
    private lateinit var loadingGif: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userCard = view.findViewById(R.id.user_description)
        name = view.findViewById(R.id.name_info)
        bloodType = view.findViewById(R.id.blood_type)
        loadingGif = view.findViewById(R.id.loading_gif)

        fillUserInfo()
        getClickListeners()
    }

    private fun fillUserInfo() {
        loadingSetup(true)

        val firebase = FirebaseHandler(this)
        firebase.retrieveData("1111")

    }

    private fun getClickListeners() {
        view?.findViewById<CardView>(R.id.card_register)?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_registerFragment)
        }

        view?.findViewById<CardView>(R.id.card_attendance)?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_checkFragment)
        }

        view?.findViewById<CardView>(R.id.card_map)?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mapsFragment   )
        }
    }

    private fun loadingSetup(isStarting: Boolean) {
        if (isStarting) {
            userCard.visibility = View.INVISIBLE
            loadingGif.visibility = View.VISIBLE
            Glide.with(this).load(R.drawable.loading_gif).into(loadingGif)
        } else {
            userCard.visibility = View.VISIBLE
            loadingGif.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    override fun actionAfterResult(userFinal: UserInfo) {
        loadingSetup(false)

        name.text = "${userFinal.infoMap[Constants.Register.FIRST_NAME]} ${userFinal.infoMap[Constants.Register.LAST_NAME]}"
        bloodType.text = userFinal.infoMap[Constants.Register.BLOOD_TYPE]
    }
}
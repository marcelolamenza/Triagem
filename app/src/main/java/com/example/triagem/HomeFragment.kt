package com.example.triagem

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
    private lateinit var userName: TextView
    private lateinit var nameLabel: TextView
    private lateinit var bloodType: TextView
    private lateinit var loadingGif: ImageView
    private var userID = "-1"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getXmlInfo(view)
        fillUserCardInfo()
        getClickListeners()
    }

    private fun getXmlInfo(view: View) {
        userName = view.findViewById(R.id.name_info)
        userCard = view.findViewById(R.id.user_description)
        nameLabel = view.findViewById(R.id.name_label)
        bloodType = view.findViewById(R.id.blood_type)
        loadingGif = view.findViewById(R.id.loading_gif)
    }

    private fun fillUserCardInfo() {
        loadAnimationSetup(true)
        retrieveLoginInfo()

        val firebase = FirebaseHandler(this)
        firebase.retrieveUserData(userID)
    }

    private fun getClickListeners() {
        view?.findViewById<CardView>(R.id.card_register)?.setOnClickListener {
            //findNavController().navigate(R.id.action_homeFragment_to_registerFragment)
        }

        view?.findViewById<CardView>(R.id.card_attendance)?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_checkFragment)
        }

        view?.findViewById<CardView>(R.id.card_map)?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mapsFragment)
        }
    }

    private fun loadAnimationSetup(isStarting: Boolean) {
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
    override fun fillLayoutWithUserInfo(userFinal: UserInfo) {
        loadAnimationSetup(false)

        if (userFinal.id != Constants.User.NO_USER) {
            userName.text = "${userFinal.infoMap?.get(Constants.User.FIRST_NAME)} ${
                userFinal.infoMap?.get(Constants.User.LAST_NAME)
            }"
            bloodType.text = userFinal.infoMap?.get(Constants.User.BLOOD_TYPE) ?: ""
        } else {
            nameLabel.text = "Nenhum usuario encontrado"
        }
    }

    private fun retrieveLoginInfo() {
        if (arguments != null) {
            val bundle = arguments
            if (bundle == null) {
                Log.e(Constants.LogMessage.ERROR, "Didn't receive information from LoginFragment")
            } else {
                val args = HomeFragmentArgs.fromBundle(bundle)
                userID = args.cpf
            }
        }
    }
}
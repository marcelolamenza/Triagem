package com.example.triagem

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
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
import com.example.triagem.util.SharedPrefHandler

class HomeFragment : Fragment(), FirebaseCallback {

    private lateinit var userCard: ConstraintLayout
    private lateinit var userName: TextView
    private lateinit var nameLabel: TextView
    private lateinit var bloodType: TextView
    private lateinit var loadingGif: ImageView
    private var userID = Constants.User.NO_USER

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
        saveIdPreference()


    }

    private fun saveIdPreference() {
        val sharedPref = SharedPrefHandler(requireActivity())
        sharedPref.saveString(Constants.User.CPF, userID)
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
        view?.findViewById<ImageButton>(R.id.edit_button)?.setOnClickListener {
            val directions = HomeFragmentDirections.actionHomeFragmentToRegisterFragment(userID, true)
            findNavController().navigate(directions)
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
            Glide.with(this).load(R.drawable.loading_purple).into(loadingGif)
        } else {
            userCard.visibility = View.VISIBLE
            loadingGif.visibility = View.GONE
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onDatabaseResponse(userFinal: UserInfo) {
        loadAnimationSetup(false)

        if (userFinal.id != Constants.User.NO_USER) {
            val firstName = userFinal.infoMap?.get(Constants.User.FIRST_NAME)
            var lastName = userFinal.infoMap?.get(Constants.User.LAST_NAME)
            lastName = lastName?.substringBefore(" ")

            userName.text = "$firstName $lastName"
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
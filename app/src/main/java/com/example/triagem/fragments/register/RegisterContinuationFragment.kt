package com.example.triagem.fragments.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.triagem.R
import com.example.triagem.models.UserInfo
import com.example.triagem.util.Constants
import com.example.triagem.util.CustomToast
import com.example.triagem.util.FirebaseHandler

class RegisterContinuationFragment : Fragment() {
    private lateinit var userInfo: UserInfo
    private var isEditing = false
    private lateinit var bloodTypeSpinner: Spinner
    private lateinit var diseaseEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retrieveRegisterInfo()

        return inflater.inflate(R.layout.fragment_register_continuation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createSpinner(view)

        diseaseEditText = requireView().findViewById<EditText>(R.id.diseases)

        if (isEditing) {
            diseaseEditText.setText(userInfo.infoMap?.get(Constants.User.DISEASES) ?: "O dev fez alguma besteira")
        }

        val button = view.findViewById<Button>(R.id.btn_register)
        button.setOnClickListener {

            addAdditionalInformation()

            if (isEditing) {
                updateDatabase()
            } else {
                saveOnDatabase()

                CustomToast.showBottom(requireActivity(), "Usuário adicionado com sucesso!")
            }

            val directions = RegisterContinuationFragmentDirections.actionRegisterDetailsFragmentToHomeFragment(userInfo.id)
            findNavController().navigate(directions)
        }
    }

    private fun retrieveRegisterInfo() {
        val bundle = arguments
        if (bundle == null) {
            Log.e(Constants.LogMessage.ERROR, "Didn't receive information from RegisterFragment")
        } else {
            val args = RegisterContinuationFragmentArgs.fromBundle(bundle)
            userInfo = args.user
            isEditing = args.isEditing
        }
    }

    private fun addAdditionalInformation() {

        appendUserData(bloodTypeSpinner.selectedItem.toString(), diseaseEditText.text.toString())
    }

    private fun appendUserData(bloodType: String, diseases: String) {
        userInfo.infoMap?.set(Constants.User.BLOOD_TYPE, bloodType)
        userInfo.infoMap?.set(Constants.User.DISEASES, diseases)
    }

    private fun saveOnDatabase() {
        val fbStorage = FirebaseHandler()
        fbStorage.startSaving(userInfo)
    }

    private fun updateDatabase() {
        val fbStorage = FirebaseHandler()
        fbStorage.startUpdating(userInfo)
    }

    private fun createSpinner(view: View) {
        bloodTypeSpinner = view.findViewById(R.id.blood_type)
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.blood_type_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                bloodTypeSpinner.adapter = adapter
            }
        }
    }
}
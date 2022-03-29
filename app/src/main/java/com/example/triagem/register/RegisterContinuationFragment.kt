package com.example.triagem.register

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
import com.example.triagem.util.FirebaseHandler

class RegisterContinuationFragment : Fragment() {
    private lateinit var userInfo: UserInfo
    private lateinit var spinner: Spinner

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

        val button = view.findViewById<Button>(R.id.btn_register)
        button.setOnClickListener{
            fillUserData()
            startDatabaseRoutine()

            Toast.makeText(context, "Usuário adicionado com sucesso!", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_registerDetailsFragment_to_homeFragment)
        }
    }

    private fun retrieveRegisterInfo() {
        val bundle = arguments
        if (bundle == null) {
            Log.e(Constants.LogMessage.ERROR, "Didn't receive information from RegisterFragment")
        }else{
            val args = RegisterContinuationFragmentArgs.fromBundle(bundle)
            userInfo = args.user
        }
    }

    private fun fillUserData() {
        val bloodTypeSpinner = view?.findViewById<Spinner>(R.id.blood_type)
        val diseaseEditText = view?.findViewById<EditText>(R.id.diseases)
        appendUserData(bloodTypeSpinner?.selectedItem.toString(), diseaseEditText.toString())
    }

    private fun appendUserData(bloodType: String, diseases: String) {
        userInfo.detail[Constants.Register.BLOOD_TYPE] = bloodType
        userInfo.detail[Constants.Register.DISEASES] = diseases
    }

    private fun startDatabaseRoutine() {
        val fbStorage = FirebaseHandler()
        fbStorage.startSaving(userInfo.detail)
    }

    private fun createSpinner(view: View) {
        spinner = view.findViewById(R.id.blood_type)
        // Create an ArrayAdapter using the string array and a default spinner layout
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.blood_type_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinner.adapter = adapter
            }
        }
    }
}
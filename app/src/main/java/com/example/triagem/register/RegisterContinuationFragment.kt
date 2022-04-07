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
        button.setOnClickListener {
            addAdditionalUserInformation()
            saveOnDatabase()

            Toast.makeText(context, "Usuário adicionado com sucesso!", Toast.LENGTH_LONG).show()

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
        }
    }

    private fun addAdditionalUserInformation() {
        val bloodTypeSpinner = view?.findViewById<Spinner>(R.id.blood_type)
        val diseaseEditText = requireView().findViewById<EditText>(R.id.diseases)

        appendUserData(bloodTypeSpinner?.selectedItem.toString(), diseaseEditText.text.toString())
    }

    private fun appendUserData(bloodType: String, diseases: String) {
        userInfo.infoMap?.set(Constants.User.BLOOD_TYPE, bloodType)
        userInfo.infoMap?.set(Constants.User.DISEASES, diseases)
    }

    private fun saveOnDatabase() {
        val fbStorage = FirebaseHandler()
        fbStorage.startSaving(userInfo)
    }

    private fun createSpinner(view: View) {
        spinner = view.findViewById(R.id.blood_type)
        context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.blood_type_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        }
    }
}
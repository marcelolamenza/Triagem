package com.example.triagem.fragments.maps

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.triagem.R
import com.example.triagem.hospitalCommunication.*
import com.example.triagem.models.HospitalInfo
import com.example.triagem.util.Constants
import com.example.triagem.util.SharedPrefHandler
import com.google.android.gms.maps.model.PointOfInterest

class MapsDialog(private val poi: PointOfInterest, private val dialogCallback: DialogCallback) : DialogFragment(), MockedHospitalCallback {
    lateinit var titleTextView: TextView
    lateinit var actionButton: Button
    lateinit var capacity: TextView
    lateinit var hospitalCommunication: HospitalCommunicationInterface

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner)

        return inflater.inflate(R.layout.dialog_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        titleTextView = view.findViewById(R.id.title)
        actionButton = view.findViewById(R.id.incorrect_choice_button)

        val sharedPref = SharedPrefHandler(requireActivity())
        val isViewMode = sharedPref.getBoolean(Constants.Maps.IS_VIEW_MODE)

        if (isViewMode) {
            actionButton.text = "Fechar"
        }

        actionButton.setOnClickListener {
            dialogCallback.dialogClick()
        }

        capacity = view.findViewById(R.id.capacity)
        hospitalCommunication = MockedHospitalCommunication(poi, this)
        hospitalCommunication.getHospitalInformation()


        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    @SuppressLint("SetTextI18n")
    override fun fillHospitalInformation(hospitalInfo: HospitalInfo) {
        val sharedPrefHandler = SharedPrefHandler(requireActivity())
        sharedPrefHandler.saveDataClass(Constants.SharedPref.CURRENT_HOSPITAL, hospitalInfo)

        titleTextView.text = hospitalInfo.name
        capacity.text = "Lotação: ${hospitalInfo.actualPopulation}/ ${hospitalInfo.totalCapacity}"
    }

    interface DialogCallback {
        fun dialogClick()
    }
}
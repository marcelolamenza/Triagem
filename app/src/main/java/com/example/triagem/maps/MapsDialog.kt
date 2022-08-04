package com.example.triagem.maps

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.triagem.R
import com.example.triagem.hospitalCommunication.*
import com.example.triagem.models.HospitalInfo
import com.google.android.gms.maps.model.PointOfInterest

class MapsDialog(private val poi: PointOfInterest) : DialogFragment(), MockedHospitalCallback {
    lateinit var titleTextView: TextView
    lateinit var button: Button
    lateinit var capacity: TextView

    //    private var title: String = ""
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
//        titleTextView.text = title

        button = view.findViewById(R.id.button)
        button.setOnClickListener {
            action()
        }

        capacity = view.findViewById(R.id.capacity)
        hospitalCommunication = MockedHospitalCommunication(poi, this)

        super.onViewCreated(view, savedInstanceState)
    }

    private fun action() {
        //TODO("Not yet implemented")
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    @SuppressLint("SetTextI18n")
    override fun fillHospitalInformation(hospitalInfo: HospitalInfo) {
        titleTextView.text = hospitalInfo.name
        capacity.text = "Lotação: ${hospitalInfo.actualPopulation}/ ${hospitalInfo.totalCapacity}"
    }

}
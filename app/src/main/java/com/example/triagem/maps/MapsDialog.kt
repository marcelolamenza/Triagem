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
import com.example.triagem.hospitalCommunication.FirebaseHospitalCommunication
import com.example.triagem.hospitalCommunication.HospitalCallback
import com.example.triagem.hospitalCommunication.HospitalCommunicationInterface

class MapsDialog(
    private val fragManager: FragmentManager,
    private val placeId: String
) : DialogFragment(), HospitalCallback {
    lateinit var titleTextView: TextView
    lateinit var button: Button
    lateinit var capacity: TextView
    private var title: String = ""
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
        titleTextView.text = title

        button = view.findViewById(R.id.button)
        button.setOnClickListener {
            action()
        }

        capacity = view.findViewById(R.id.capacity)
        hospitalCommunication = FirebaseHospitalCommunication(placeId, this)

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

    fun customShow(dialogTitle: String) {
        title = dialogTitle
        show(fragManager, "TAG")
    }

    @SuppressLint("SetTextI18n")
    override fun fillHospitalCapacity(actual: Long, total: Long) {
        capacity.text = "Lotação: $actual/ $total"
    }

}
package com.example.triagem.hospitalCommunication

import com.example.triagem.models.HospitalInfo
import com.google.android.gms.maps.model.PointOfInterest

class MockedHospitalCommunication(val poi: PointOfInterest, val hospitalCallback: MockedHospitalCallback) : HospitalCommunicationInterface {
    init {
        retrieveHospitalData()
    }

    override fun sendInformation() {
        TODO("Not yet implemented")
    }

    override fun getHospitalCapacity(): Long {
        TODO("Not yet implemented")
    }

    override fun getPatientsList() {
        TODO("Not yet implemented")
    }

    override fun getPatientsTotal(): Long {
        TODO("Not yet implemented")
    }

    private fun retrieveHospitalData() {
        val maxCapacity = generateRandomNumber(100)
        val actualCapacity = generateRandomNumber(maxCapacity)

        val hospitalInfo = HospitalInfo(poi.name, poi.placeId, actualCapacity.toLong(), maxCapacity.toLong())

        hospitalCallback.fillHospitalInformation(hospitalInfo)
    }

    private fun generateRandomNumber(maxVal: Int) : Int {
        return (1..maxVal).random()
    }
}

interface MockedHospitalCallback {
    fun fillHospitalInformation(hospitalInfo: HospitalInfo)
}
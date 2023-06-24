package com.example.triagem.hospitalCommunication

import com.example.triagem.models.HospitalInfo
import com.google.android.gms.maps.model.PointOfInterest

class MockedHospitalCommunication(
    private val poi: PointOfInterest,
    private val hospitalCallback: MockedHospitalCallback
) : HospitalCommunicationInterface {

    override fun sendInformation() {
        TODO("Not yet implemented")
    }

    override fun getHospitalInformation(): Long {
        val maxCapacity = generateRandomNumber(100)
        val actualCapacity = generateRandomNumber(maxCapacity)

        val hospitalInfo =
            HospitalInfo(poi.name, poi.placeId, actualCapacity.toLong(), maxCapacity.toLong())

        hospitalCallback.fillHospitalInformation(hospitalInfo)

        return 0
    }

    override fun getPatientsList() {
        TODO("Not yet implemented")
    }

    override fun getPatientsTotal(): Long {
        TODO("Not yet implemented")
    }

    private fun generateRandomNumber(maxVal: Int): Int {
        return (1..maxVal).random()
    }
}

interface MockedHospitalCallback {
    fun fillHospitalInformation(hospitalInfo: HospitalInfo)
}
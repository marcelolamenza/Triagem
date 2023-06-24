package com.example.triagem.hospitalCommunication

import com.example.triagem.models.HospitalInfo
import com.google.android.gms.maps.model.PointOfInterest

class MockedHospitalCommunication(private val poi: PointOfInterest): HospitalCommunicationInterface {

    override fun sendInformation() {
        TODO("Not yet implemented")
    }

    override fun getHospitalInformation(): HospitalInfo {
        val maxCapacity = generateRandomNumber(100)
        val actualCapacity = generateRandomNumber(maxCapacity)

        return HospitalInfo(poi.name, poi.placeId, actualCapacity.toLong(), maxCapacity.toLong())
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
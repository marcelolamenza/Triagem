package com.example.triagem.hospitalCommunication

import com.example.triagem.models.HospitalInfo
import com.example.triagem.models.HospitalQueue
import com.google.android.gms.maps.model.PointOfInterest

class MockedHospitalCommunication(private val poi: PointOfInterest) :
    HospitalCommunicationInterface {

    override fun sendInformation() {
        TODO("Not yet implemented")
    }

    override fun getHospitalInformation(): HospitalInfo {
        val maxCapacity = generateRandomNumber(100)
        val actualCapacity = generateRandomNumber(maxCapacity)

        val hospitalQueue = splitPatientsByColor(actualCapacity)

        return HospitalInfo(poi.name, poi.placeId, hospitalQueue, maxCapacity.toLong())
    }

    private fun splitPatientsByColor(actualCapacity: Int): HospitalQueue {
        val blue = generateNumberBetween(actualCapacity)
        var currentCapacity = actualCapacity - blue

        val green = generateNumberBetween(currentCapacity)
        currentCapacity -= green

        val yellow = generateNumberBetween(currentCapacity)
        currentCapacity -= yellow

        val orange = generateNumberBetween(currentCapacity)

        val red = currentCapacity - orange

        return HospitalQueue(red, orange, yellow, green, blue)
    }

    override fun getPatientsList() {
        TODO("Not yet implemented")
    }

    override fun getPatientsTotal(): Long {
        TODO("Not yet implemented")
    }

    private fun generateRandomNumber(maxVal: Int): Int {
        var rand = 0
        while (rand < (maxVal * 0.6) || rand > (maxVal * 0.9)) {
            rand = (1..maxVal).random()
        }

        return rand
    }

    private fun generateNumberBetween(maxVal: Int): Int {
        var rand = 0
        while (rand < (maxVal * 0.3) || rand > (maxVal * 0.6)) {
            rand = (1..maxVal).random()
        }

        return rand
    }
}
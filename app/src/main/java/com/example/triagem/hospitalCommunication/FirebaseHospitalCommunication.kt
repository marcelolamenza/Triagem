package com.example.triagem.hospitalCommunication

import android.util.Log
import com.example.triagem.models.HospitalInfo
import com.example.triagem.util.Constants
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseHospitalCommunication(id: String, val hospitalCallback: HospitalCallback) : HospitalCommunicationInterface {
    private lateinit var hospitalDatabase: CollectionReference
    private var hospitalInfo: HospitalInfo

    init {
        hospitalInfo = retrieveHospitalData(id)
    }

    override fun sendInformation() {
       // TODO("Not yet implemented")
    }

    override fun getHospitalCapacity(): Long {
        return hospitalInfo.totalCapacity
    }

    override fun getPatientsList() {
        TODO("Not yet implemented")
    }

    override fun getPatientsTotal(): Long {
        return hospitalInfo.actualPopulation
    }

    private fun findDatabase() {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        hospitalDatabase = db.collection(Constants.Firebase.DB_NAME_HOSPITAL)
    }

    private fun updateFilledPopulation(hospitalInfo: HospitalInfo) {

        if (hospitalInfo.actualPopulation == hospitalInfo.totalCapacity) {
            Log.e(Constants.LogMessage.ERROR, "Hospital maximum capacity")
        } else {
            hospitalInfo.actualPopulation++

            hospitalDatabase.document(hospitalInfo.id)
                .update(
                    mapOf(
                        Constants.Hospitals.ACTUAL_CAPACITY to hospitalInfo.actualPopulation
                    )
                )
        }
    }

    private fun retrieveHospitalData(hospitalId: String): HospitalInfo {
        findDatabase()

        hospitalInfo = HospitalInfo("", "", 0, 0)

        hospitalDatabase.document(hospitalId).get()
            .addOnSuccessListener { document ->

                hospitalInfo = HospitalInfo(
                    name = document[Constants.Hospitals.NAME].toString(),
                    id = document[Constants.Hospitals.ID].toString(),
                    actualPopulation = document[Constants.Hospitals.ACTUAL_CAPACITY] as Long,
                    totalCapacity = document[Constants.Hospitals.TOTAL_CAPACITY] as Long
                )

                hospitalCallback.fillHospitalCapacity(hospitalInfo.actualPopulation, hospitalInfo.totalCapacity)
            }
            .addOnFailureListener { exception ->
                Log.d(Constants.LogMessage.TAG, "get failed with ", exception)
            }

        Log.e("TAG", " ACTUAL POPULATION ${hospitalInfo.actualPopulation}")

        return hospitalInfo
    }

}

interface HospitalCallback {
    fun fillHospitalCapacity(actual: Long, total: Long)
}
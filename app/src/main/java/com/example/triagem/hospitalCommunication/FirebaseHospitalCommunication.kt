package com.example.triagem.hospitalCommunication

import android.util.Log
import com.example.triagem.models.HospitalInfo
import com.example.triagem.util.Constants
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseHospitalCommunication(val id: String, val hospitalCallback: HospitalCallback) : HospitalCommunicationInterface {
    private lateinit var hospitalDatabase: CollectionReference

    init {
        retrieveHospitalData(id)
    }

    override fun sendInformation() {
       // TODO("Not yet implemented")
    }

    override fun getHospitalCapacity(): Long {
        return 0
    }

    override fun getPatientsList() {
        TODO("Not yet implemented")
    }

    override fun getPatientsTotal(): Long {
        return 0
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

    private fun retrieveHospitalData(hospitalId: String) {
        findDatabase()

        hospitalDatabase.document(hospitalId).get()
            .addOnSuccessListener { document ->

                val hospitalInfo = HospitalInfo(
                    name = document[Constants.Hospitals.NAME].toString(),
                    id = id,
                    actualPopulation = document[Constants.Hospitals.ACTUAL_CAPACITY] as Long,
                    totalCapacity = document[Constants.Hospitals.TOTAL_CAPACITY] as Long
                )

                hospitalCallback.fillHospitalCapacity(hospitalInfo)
            }
            .addOnFailureListener { exception ->
                Log.d(Constants.LogMessage.TAG, "get failed with ", exception)
            }
    }

}

interface HospitalCallback {
    fun fillHospitalCapacity(hospitalInfo: HospitalInfo)
}
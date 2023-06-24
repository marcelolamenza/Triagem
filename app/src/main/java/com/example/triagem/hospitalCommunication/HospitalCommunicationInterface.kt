package com.example.triagem.hospitalCommunication

import com.example.triagem.models.HospitalInfo

interface HospitalCommunicationInterface {
    fun sendInformation()
    fun getHospitalInformation(): HospitalInfo
    fun getPatientsList()
    fun getPatientsTotal(): Long
}


// Cada hospital tem uma limitação de pacientes, uma quantidade de médicos
//Cada paciente vai estar associado a um
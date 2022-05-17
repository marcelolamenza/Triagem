package com.example.triagem.hospitalCommunication

interface HospitalCommunicationInterface {
    fun sendInformation()
    fun getHospitalCapacity(): Long
    fun getPatientsList()
    fun getPatientsTotal(): Long
}


// Cada hospital tem uma limitação de pacientes, uma quantidade de médicos
//Cada paciente vai estar associado a um
package com.example.triagem.models

data class HospitalQueue(var red: Int, var orange: Int, var yellow: Int, var green: Int, var blue: Int) {
    fun getTotal(): Int {
        return red + orange + yellow + blue + green
    }
}

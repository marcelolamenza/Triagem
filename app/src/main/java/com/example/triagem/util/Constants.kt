package com.example.triagem.util

object Constants {
    object User {
        const val FIRST_NAME = "Nome"
        const val LAST_NAME = "Sobrenome"
        const val PHONE = "Telefone"
        const val ADDRESS = "Endereço"
        const val EMAIL = "Email"
        const val RG = "RG"
        const val CPF = "CPF"
        const val BLOOD_TYPE = "Tipo sanguineo"
        const val DISEASES = "Doenças"

        const val NO_USER = "-1"
    }

    object Hospitals {
        const val NAME = "name"
        const val ID = "id"
        const val TOTAL_CAPACITY = "total_capacity"
        const val ACTUAL_CAPACITY = "actual_capacity"
    }

    object Firebase {
        const val DB_NAME_USERS = "users"
        const val DB_NAME_HOSPITAL = "hospitals"
    }

    object LogMessage {
        const val TAG = "logTag"
        const val ERROR = "error"
    }
}
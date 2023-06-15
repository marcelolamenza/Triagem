package com.example.triagem.util

object Constants {
    object User {
        const val FIRST_NAME = "Nome"
        const val LAST_NAME = "Sobrenome"
        const val PHONE = "Telefone"
        const val ADDRESS = "Endereço"
        const val EMAIL = "Email"
        const val PASSWORD = "Senha"
        const val RG = "RG"
        const val CPF = "CPF"
        const val BLOOD_TYPE = "Tipo sanguineo"
        const val DISEASES = "Doenças"

        const val TRIAGE_RESULT = "Triage_result"

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
        const val DB_NAME_USER_QUEUE = "user_queue"
    }

    object LogMessage {
        const val TAG = "log_tag"
        const val ERROR = "error"
        const val DEV_ERROR = "O dev fez algo de errado"
    }

    object Maps {
        const val IS_VIEW_MODE = "view_mode";
    }

    object SharedPref {
        const val NAME = "shared"
        const val FIRST_LOGIN = "firstLogin"
        const val DEFAULT_VALUE = ""
        const val LOGIN = "login"
    }
}
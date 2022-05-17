package com.example.triagem.util

enum class PatientState {
    FIRST_SLOT, RED, ORANGE, YELLOW, GREEN, BLUE, UNMAPPED {
        override fun next(): PatientState? {
            return null
        }
    };

    open operator fun next(): PatientState? {
        return PatientState.values()[ordinal + 1]
    }
}
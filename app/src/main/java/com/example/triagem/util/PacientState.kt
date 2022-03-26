package com.example.triagem.util

enum class PacientState {
    FIRST_SLOT, RED, ORANGE, YELLOW, GREEN, BLUE, UNMAPPED {
        override fun next(): PacientState? {
            return null // see below for options for this line
        }
    };

    open operator fun next(): PacientState? {
        // No bounds checking required here, because the last instance overrides
        return PacientState.values()[ordinal + 1]
    }
}
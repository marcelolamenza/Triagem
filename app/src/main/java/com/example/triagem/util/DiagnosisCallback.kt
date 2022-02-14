package com.example.triagem.util

import android.widget.Button

interface DiagnosisCallback {
    fun nextFrag(button: Button, isClicked: Boolean)
}
package com.example.triagem.diagnosis

import android.widget.Button

interface DiagnosisCallback {
    fun clickAction(button: Button, isClicked: Boolean)
}
package com.example.triagem.util

import android.widget.Button

interface DiagnosisCallback {
    fun clickAction(button: Button, isClicked: Boolean)
}
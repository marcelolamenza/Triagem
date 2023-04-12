package com.example.triagem.fragments.diagnosis

import android.widget.Button

interface DiagnosisCallback {
    fun clickAction(button: Button, isSelected: Boolean, position: Int)
}
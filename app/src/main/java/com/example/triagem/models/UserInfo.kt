package com.example.triagem.models

import android.os.Parcelable
import com.example.triagem.util.PatientState
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInfo(var id: String, var infoMap: HashMap<String, String>?, var treatLevel: PatientState?): Parcelable
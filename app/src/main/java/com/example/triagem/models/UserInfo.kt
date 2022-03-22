package com.example.triagem.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserInfo(var detail: HashMap<String, String>): Parcelable

data class Information(val label: String, val info: String)
package com.example.triagem.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.triagem.models.HospitalInfo
import com.google.gson.Gson

class SharedPrefHandler(private val activity: Activity) {
    var preferences: SharedPreferences =
        activity.getSharedPreferences(Constants.SharedPref.NAME, Context.MODE_PRIVATE)

    fun saveString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return preferences.getString(key, Constants.SharedPref.DEFAULT_VALUE)
    }

    fun saveBoolean(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }

    fun saveDataClass(key: String, data: Any) {
        val gson = Gson()
        val jsonString = gson.toJson(data)
        preferences.edit().putString(key, jsonString).apply()
    }

    inline fun <reified T> getDataClass(key: String): T? {
        val gson = Gson()
        val jsonString = preferences.getString(key, null)
        return gson.fromJson(jsonString, T::class.java)
    }
}
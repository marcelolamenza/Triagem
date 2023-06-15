package com.example.triagem.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

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
}
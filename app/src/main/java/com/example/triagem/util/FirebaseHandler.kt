package com.example.triagem.util

import android.util.Log
import com.example.triagem.models.UserInfo
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseHandler(private val firebaseCallback: FirebaseCallback? = null) {
    private lateinit var databaseUsers: CollectionReference

    fun startSaving(userInfo: UserInfo) {
        findDatabase()
        saveDataToDocument(userInfo)
    }

    private fun findDatabase() {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        databaseUsers = db.collection(Constants.Firebase.DB_NAME_USERS)
    }

    private fun saveDataToDocument(userInfo: UserInfo) {
        Log.d(Constants.LogMessage.TAG, "\nID: ${userInfo.id} \nDATA: ${userInfo.infoMap}\n")

        userInfo.infoMap?.let { databaseUsers.document(userInfo.id).set(it) }
    }

    fun retrieveUserData(userId: String) {
        findDatabase()

        databaseUsers.document(userId).get()
            .addOnSuccessListener { userInfo ->
                if (userInfo != null) {
                    sendInfoToFragment(userId, userInfo.data as HashMap<String, String>?)
                } else {
                    sendInfoToFragment(Constants.User.NO_USER, null)
                }
            }
            .addOnFailureListener { exception ->
                Log.d(Constants.LogMessage.TAG, "get failed with AAAAAA", exception)
            }
    }

    private fun sendInfoToFragment(id: String, data: HashMap<String, String>?) {
        val user = UserInfo(id, data)
        firebaseCallback?.onDatabaseResponse(user)
        Log.d(Constants.LogMessage.TAG, "DocumentSnapshot data: $data")
    }
}

interface FirebaseCallback {
    fun onDatabaseResponse(userFinal: UserInfo)
}
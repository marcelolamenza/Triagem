package com.example.triagem.util

import android.util.Log
import com.example.triagem.models.UserInfo
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseHandler(val firebaseCallback: FirebaseCallback) {
    private lateinit var databaseUsers: CollectionReference



//    var a = HashMap <String, String>()
//

//    var userFinal = UserInfo("", a)

    fun startSaving(userInfo: UserInfo) {
        findDatabase()
        saveDataToDocument(userInfo)
    }

    private fun findDatabase() {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        databaseUsers = db.collection(Constants.Firebase.DB_NAME_USERS)
    }

    private fun saveData(userHashMap: HashMap<String, String>) {
        databaseUsers.add(userHashMap)
            .addOnSuccessListener { documentReference ->
                Log.d("LOG", "Document added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("LOG", "Error adding document", e)
            }
    }

    private fun saveDataToDocument(userInfo: UserInfo) {
        Log.d("TAG", "\nID: ${userInfo.id} \nDATA: ${userInfo.infoMap}\n")

        databaseUsers.document(userInfo.id).set(userInfo.infoMap)
    }

    val TAG = "HAHAHA"
    fun retrieveData(userId: String) {
        findDatabase()
        val dbInformation = databaseUsers.document(userId).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val userFinal = UserInfo(userId, document.data as HashMap<String, String>)
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    Log.d(TAG, "ID: ${userFinal.id}")
                    Log.d(TAG, "BLOOD: ${userFinal.infoMap[Constants.Register.BLOOD_TYPE]}")

                    firebaseCallback.actionAfterResult(userFinal)
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }

//        return UserInfo(userId, dbInformation.result!!.data as HashMap<String, String>)
//
    }
}

interface FirebaseCallback {
    fun actionAfterResult(userFinal: UserInfo)
}
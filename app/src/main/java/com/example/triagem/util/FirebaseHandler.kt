package com.example.triagem.util

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseHandler {
    private lateinit var databaseUsers: CollectionReference
    val users = hashMapOf<String, String>()

    private fun findDatabase() {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        databaseUsers = db.collection(Constants.Firebase.DB_NAME_USERS)
    }

    private fun saveData(currentUser: HashMap<String, String>) {
        databaseUsers.add(currentUser)
            .addOnSuccessListener { documentReference ->
                Log.d("LOG", "Document added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("LOG", "Error adding document", e)
            }
    }
}
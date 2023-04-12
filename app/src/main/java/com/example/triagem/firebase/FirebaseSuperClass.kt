package com.example.triagem.firebase

import com.example.triagem.util.Constants
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

sealed class FirebaseSuperClass {
    fun findDatabase(databaseName: String): CollectionReference {
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        return db.collection(databaseName)
    }
}


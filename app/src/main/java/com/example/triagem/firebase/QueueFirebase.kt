package com.example.triagem.firebase

import android.util.Log
import com.example.triagem.models.UserInfo
import com.example.triagem.util.Constants
import com.example.triagem.util.FirebaseCallback
import com.google.firebase.firestore.CollectionReference

class QueueFirebase(private val firebaseCallback: FirebaseCallback? = null): FirebaseSuperClass() {
    private lateinit var queueDB: CollectionReference

    init {
        findDatabase(Constants.Firebase.DB_NAME_USER_QUEUE)
    }

    fun startSaving(userInfo: UserInfo) {
        Log.d(Constants.LogMessage.TAG, "\nID: ${userInfo.id} \nDATA: ${userInfo.infoMap}\n")



        if (queueDB.document(userInfo.id).get()
            != null) {

        }

        userInfo.infoMap?.let { queueDB.document(userInfo.id).set(it) }
    }

    fun retrieveUserData(userId: String) {
        queueDB.document(userId).get()
            .addOnSuccessListener { userInfo ->
                if (userInfo != null) {
                    sendInfoToFragment(userId, userInfo.data as HashMap<String, String>?)
                } else {
                    sendInfoToFragment(Constants.User.NO_USER, null)
                }
            }
            .addOnFailureListener { exception ->
                Log.e(Constants.LogMessage.TAG, "get failed with AAAAAA", exception)
            }
    }

    private fun sendInfoToFragment(id: String, data: HashMap<String, String>?) {
        val user = UserInfo(id, data)
        firebaseCallback?.onDatabaseResponse(user)
        Log.d(Constants.LogMessage.TAG, "DocumentSnapshot data: $data")
    }

    fun startUpdating(userInfo: UserInfo) {
        userInfo.infoMap?.let { queueDB.document(userInfo.id).update(it as Map<String, Any>) }
    }

}
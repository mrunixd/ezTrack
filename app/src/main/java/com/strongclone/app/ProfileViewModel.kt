package com.strongclone.app

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileViewModel : ViewModel() {
    private val TAG = "ProfileViewModel"
    private val firestore = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    fun getName(
        onSuccess: (String) -> Unit,
        onFailure: () -> Unit
    ) {
        if (userId != null) {
            firestore.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val firstName = document.getString("firstName") ?: ""
                        val lastName = document.getString("lastName") ?: ""
                        onSuccess("$firstName $lastName")
                    } else {
                        Log.d(TAG, "Document not found")
                        onFailure()
                    }
                }
                .addOnFailureListener { e ->
                    Log.d(TAG, "Error fetching name: ${e.message}", e)
                    onFailure()
                }
        } else {
            Log.d(TAG, "User ID is null")
            onFailure()
        }
    }
}

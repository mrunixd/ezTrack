package com.strongclone.app

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.Auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class AuthViewModel: ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        if (auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            checkUserDetails(auth.currentUser!!.uid)
        }
    }

    fun signIn(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or Password can't be empty")
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
                }
            }
    }

    fun signUp(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or Password can't be empty")
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.AuthenticatedButRequireDetails
                } else {
                    _authState.value = AuthState.Error(task.exception?.message?:"Something went wrong")
                }
            }
    }

    fun signOut() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }

    fun addUserToDB(firstName: String, lastName: String, height: String, weight: String) {
        val user = User(firstName,lastName, height.toInt(), weight.toInt())
        val userId = Firebase.auth.currentUser?.uid

        firestore.collection("users")
            .document(userId.toString())
            .set(user)
            .addOnSuccessListener {
                Log.d(TAG, "addUserToDB: Success")
                _authState.value = AuthState.Authenticated
            }
            .addOnFailureListener {
                Log.d(TAG, "addUserToDB: Failure")
            }
    }

    private fun checkUserDetails(userId: String) {
        firestore.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    _authState.value = AuthState.Authenticated
                } else {
                    _authState.value = AuthState.AuthenticatedButRequireDetails
                }
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "checkUserDetails: Failure", e)
                _authState.value = AuthState.Error(e.message ?: "Failed to check user details")
            }
    }
}

sealed class AuthState {
    object Authenticated: AuthState()
    object Unauthenticated: AuthState()
    object Loading: AuthState()
    object AuthenticatedButRequireDetails: AuthState()

    data class Error(val message: String) : AuthState()
}
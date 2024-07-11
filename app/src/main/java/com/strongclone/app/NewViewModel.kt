package com.strongclone.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewViewModel : ViewModel() {
    private val _workouts = MutableStateFlow<List<String>>(emptyList())
    val workouts: StateFlow<List<String>> = _workouts

    init {
        getWorkouts()
    }

    private fun getWorkouts() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            FirebaseFirestore.getInstance().collection("users")
                .document(userId)
                .collection("workoutTemplates")
                .get()
                .addOnSuccessListener { documents ->
                    val workoutList = mutableListOf<String>()
                    for (document in documents) {
                        val workoutName = document.getString("name")
                        if (workoutName != null) {
                            workoutList.add(workoutName)
                        }
                    }
                    _workouts.value = workoutList
                }
                .addOnFailureListener {
                    // Handle failure
                }
        }
    }
}

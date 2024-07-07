package com.strongclone.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewWorkoutViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    private val _workoutSavedState = MutableStateFlow(false)
    val workoutSavedState: StateFlow<Boolean> get() = _workoutSavedState.asStateFlow()

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> get() = _errorState.asStateFlow()

    fun saveWorkout(name: String, selectedExercises: List<String>) {
        val workout = hashMapOf(
            "name" to name,
            "date" to System.currentTimeMillis()
        )

        if (name.isEmpty()) {
           _errorState.value = "You forgot to name the workout!"
        } else if (selectedExercises.isEmpty()) {
            _errorState.value = "What's a workout without exercises!"
        } else {
            firestore.collection("users")
                .document(userId.toString())
                .collection("workoutTemplates")
                .add(workout)
                .addOnSuccessListener { documentReference ->
                    val workoutId = documentReference.id
                    saveExercises(workoutId, selectedExercises)
                    _workoutSavedState.value = true
                }
                .addOnFailureListener { e ->
                    _errorState.value = "Failed to save workout: ${e.message}"
                }
        }
    }

    private fun saveExercises(workoutId: String, selectedExercises: List<String>) {
        val exercisesCollection = firestore.collection("users")
            .document(userId.toString())
            .collection("workoutTemplates")
            .document(workoutId)
            .collection("exercises")

        viewModelScope.launch {
            selectedExercises.forEach { exercise ->
                val exerciseData = hashMapOf("name" to exercise)
                exercisesCollection.add(exerciseData)
                    .addOnSuccessListener { /* No-op */ }
                    .addOnFailureListener { e ->
                        _errorState.value = "Failed to save exercise: ${e.message}"
                    }
            }
        }
    }

    fun resetWorkoutSavedState() {
        _workoutSavedState.value = false
    }

    fun resetErrorState() {
        _errorState.value = null
    }
}
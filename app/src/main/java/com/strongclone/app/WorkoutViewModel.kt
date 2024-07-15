package com.strongclone.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class WorkoutViewModel : ViewModel() {
    var workoutName by mutableStateOf("Example Routine")
    var exercises by mutableStateOf(listOf<Exercise>())

    fun addExercise(exerciseName: String) {
        exercises = exercises + Exercise(exerciseName)
    }

    fun addSet(exerciseIndex: Int, reps: Int, weight: Double) {
        exercises = exercises.toMutableList().apply {
            this[exerciseIndex].sets.add(Set(reps, weight))
        }
    }
}

data class Set(val reps: Int, val weight: Double)
data class Exercise(val name: String, val sets: MutableList<Set> = mutableListOf())


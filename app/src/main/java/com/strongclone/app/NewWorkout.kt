package com.strongclone.app

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.strongclone.app.databinding.ActivityNewWorkoutBinding
import com.strongclone.app.databinding.FragmentNewBinding
import com.strongclone.app.databinding.FragmentSettingsBinding

class NewWorkout : AppCompatActivity() {
    private lateinit var binding: ActivityNewWorkoutBinding
    private lateinit var fireStore: FirebaseFirestore
    private val selectedExercises = mutableListOf<String>()
    private lateinit var selectedExercisesAdapter: SelectedExerciseAdapter
    private val exercises = listOf(
        "Arnold Press (Dumbbell)",
        "Back Extension",
        "Bench Press (Barbell)",
        "Bench Press (Dumbbell)",
        "Bench Press (Smith Machine)",
        "Bent Over Row (Dumbbell)",
        "Bent Over Row (Barbell)",
        "Bicep Curl (Dumbbell)",
        "Bicep Curl (Machine)",
        "Bicep Curl (Barbell)",
        "Bulgarian Split Squat",
        "Cable Crossover",
        "Chest Dip",
        "Pec Deck",
        "Chest Fly (Dumbbell)",
        "Chest Press (Machine)",
        "Chin Up",
        "Deadlift (Barbell)",
        "Deadlift (Dumbbell)",
        "Goblet Squat (Kettlebell)",
        "Hack Squat",
        "Incline Bench Press (Barbell)",
        "Incline Bench Press (Dumbbell)",
        "Incline Bench Press (Smith Machine)",
        "Incline Chest Press (Machine)",
        "Iso-Lateral Row (Machine)",
        "Lat Pulldown",
        "Leg Curl (Machine)",
        "Leg Extension (Machine)",
        "Leg Press (Machine)",
        "Lunge (Dumbbell)",
        "Lunge (Barbell)",
        "Overhead Press (Barbell)",
        "Overhead Press (Dumbbell)",
        "Pull Up",
        "Push Up",
        "Romanian Deadlift (Barbell)",
        "Romanian Deadlift (Dumbbell)",
        "Seated Row (Machine)",
        "Shoulder Press (Machine)",
        "Shrug (Dumbbell)",
        "Shrug (Barbell)",
        "Side Lateral Raise (Dumbbell)",
        "Squat (Barbell)",
        "Squat (Dumbbell)",
        "Squat (Smith Machine)",
        "Tricep Dip (Machine)",
        "Tricep Extension (Cable)",
        "Tricep Extension (Dumbbell)",
        "Tricep Extension (Barbell)",
        "Tricep Kickback (Dumbbell)",
        "Tricep Pushdown (Cable)",
        "Upright Row (Barbell)",
        "Upright Row (Dumbbell)",
        "Leg Raise",
        "Hanging Leg Raise",
        "Crunch",
        "Sit Up",
        "Russian Twist",
        "Plank",
        "Mountain Climber",
        "Bicycle Crunch",
        "Cable Crunch",
        "Calf Raise (Standing)",
        "Calf Raise (Seated)",
        "Reverse Fly (Dumbbell)",
        "Reverse Fly (Machine)",
        "Face Pull (Cable)",
        "Hammer Curl (Dumbbell)",
        "Preacher Curl (Barbell)",
        "Preacher Curl (Dumbbell)",
        "Preacher Curl (Machine)",
        "Incline Bicep Curl (Dumbbell)",
        "Spider Curl (Dumbbell)",
        "Spider Curl (Barbell)",
        "Hip Thrust (Barbell)",
        "Hip Thrust (Machine)",
        "Glute Bridge",
        "Sumo Deadlift",
        "Snatch (Barbell)",
        "Clean and Jerk (Barbell)",
        "Box Jump",
        "Kettlebell Swing",
        "Medicine Ball Slam",
        "Battle Rope"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fireStore = FirebaseFirestore.getInstance()

        binding.saveBtn.setOnClickListener {
            val name = binding.exerciseNameInput.text.toString().trim()
            if (name.isEmpty()) {
                Toast.makeText(this, "Enter a name for your workout.", Toast.LENGTH_SHORT).show()
            } else if (selectedExercises.isEmpty()){
                Toast.makeText(this, "Make sure to enter an exercise.", Toast.LENGTH_SHORT).show()
            } else {
                saveWorkout(name, selectedExercises)
                finish()
            }
        }

        binding.addExerciseBtn.setOnClickListener {
            showExerciseListDialog()
        }

        selectedExercisesAdapter = SelectedExerciseAdapter(this, selectedExercises)
        binding.exercisesList.adapter = selectedExercisesAdapter
    }

    private fun showExerciseListDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialogue_exercise_list, null)
        val exerciseListView: ListView = dialogView.findViewById(R.id.exerciseListView)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, exercises)
        exerciseListView.adapter = adapter

        val dialog = AlertDialog.Builder(this)
            .setTitle("Select Exercise")
            .setView(dialogView)
            .setNegativeButton("Cancel", null)
            .create()

        exerciseListView.setOnItemClickListener { parent, view, position, id ->
            val selectedExercise = exercises[position]
            selectedExercises.add(selectedExercise)
            selectedExercisesAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun saveWorkout(name: String, selectedExercises: List<String>) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val workout = hashMapOf(
            "name" to name,
            "date" to System.currentTimeMillis()
        )

        fireStore.collection("users")
            .document(userId.toString())
            .collection("workoutTemplates")
            .add(workout)
            .addOnSuccessListener { documentReference ->
                val workoutId = documentReference.id
                saveExercises(userId.toString(), workoutId, selectedExercises)
                Log.d(TAG, "saveExercises: success")
            }
            .addOnFailureListener { e -> 
                Log.d(TAG, "saveExercises: failure")
                Toast.makeText(this, "Failed to save workout: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveExercises(userId: String, workoutId: String, selectedExercises: List<String>) {
        val exercisesCollection = fireStore.collection("users")
            .document(userId)
            .collection("workoutTemplates")
            .document(workoutId)
            .collection("exercises")

        for (exercise in selectedExercises) {
            val exerciseData = hashMapOf(
                "name" to exercise
            )

            exercisesCollection.add(exerciseData)
                .addOnSuccessListener {
                    // Exercise added successfully
                    Log.d(TAG, "addExerciseToWorkoutOnDB: Success")
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to save exercise: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

        Toast.makeText(this, "Workout saved successfully", Toast.LENGTH_SHORT).show()
    }
}
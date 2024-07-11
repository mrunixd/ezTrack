package com.strongclone.app

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun NewWorkoutScreen(
    viewModel: NewWorkoutViewModel = viewModel(),
    navController: NavController
) {
    val templateNameState = remember { mutableStateOf("")}
    var selectedExercises = remember { mutableStateOf(mutableListOf<String>()) }
    var showDialog by remember { mutableStateOf(false) }
    val exerciseList = listOf(
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

    val context = LocalContext.current
    val workoutSavedState = viewModel.workoutSavedState.collectAsState()
    val errorState = viewModel.errorState.collectAsState()

    LaunchedEffect(workoutSavedState.value) {
        if (workoutSavedState.value) {
            Toast.makeText(context, "Workout saved successfully", Toast.LENGTH_SHORT).show()
            viewModel.resetWorkoutSavedState()
            navController.popBackStack()
        }
    }

    LaunchedEffect(errorState.value) {
        errorState.value?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.resetErrorState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 25.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // EditText and Save Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = templateNameState.value,
                    onValueChange = { templateNameState.value = it },
                    modifier = Modifier
                        .weight(3f)
                        .padding(vertical = 15.dp),
                    placeholder = { Text("Template name") },
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 25.sp),
                )

                Button(
                    onClick = {
                        viewModel.saveWorkout(
                            name = templateNameState.value,
                            selectedExercises = selectedExercises.value,
                        )
                    },
                    modifier = Modifier
                        .weight(1.5f)
                        .padding(start = 10.dp, top = 20.dp)
                ) {
                    Text(text = "Save", fontSize = 16.sp)
                }
            }

            // Exercises List
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(BorderStroke(2.dp, Color.Black), RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                items(selectedExercises.value.size) { index ->
                    Text(
                        text = selectedExercises.value[index],
                        modifier = Modifier.padding(5.dp),
                        fontSize = 16.sp
                    )
                }
            }

            // Add Exercise Button
            Button(
                onClick = { showDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 8.dp)
            ) {
                Text(text = "Add exercise", fontSize = 18.sp)
            }

            if (showDialog) {
                ExerciseSelectionDialog(
                    exerciseList = exerciseList,
                    onExerciseSelected = { exercise ->
                        selectedExercises.value = selectedExercises.value.toMutableList().apply { add(exercise) }
                        showDialog = false
                    },
                    onDismiss = { showDialog = false }
                )
            }
        }
    }

}

@Composable
fun ExerciseSelectionDialog(
    exerciseList: List<String>,
    onExerciseSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Select Exercise") },
        text = {
            LazyColumn {
                items(exerciseList.size) { index ->
                    Text(
                        text = exerciseList[index],
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onExerciseSelected(exerciseList[index]) }
                            .padding(16.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


@Preview
@Composable
fun NewWorkoutScreenPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 25.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 25.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // EditText and Save Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = "",
                    onValueChange = { /* Handle text change */ },
                    modifier = Modifier
                        .weight(3f)
                        .padding(vertical = 15.dp),
                    placeholder = { Text("Template name") },
                    singleLine = true,
                    textStyle = TextStyle(fontSize = 25.sp),
                )

                Button(
                    onClick = { /* Handle Save button click */ },
                    modifier = Modifier
                        .weight(1.3f)
                        .padding(start = 10.dp, top = 20.dp)
                ) {
                    Text(text = "Save", fontSize = 16.sp)
                }
            }

            // Exercises List
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                items(10) { index ->
                    Text(
                        text = "Exercise $index",
                        modifier = Modifier.padding(5.dp),
                        fontSize = 16.sp
                    )
                }
            }

            // Add Exercise Button
            Button(
                onClick = { /* Handle Add Exercise button click */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 8.dp)
            ) {
                Text(text = "Add exercise", fontSize = 18.sp)
            }
        }
    }
}
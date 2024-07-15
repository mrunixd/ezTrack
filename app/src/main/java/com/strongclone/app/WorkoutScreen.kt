package com.strongclone.app

import android.graphics.Outline
import android.graphics.drawable.Icon
import android.health.connect.datatypes.ExerciseSegment
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun WorkoutScreen(
    navController: NavController,
    viewModel: WorkoutViewModel = WorkoutViewModel()
) {
    var workoutName by remember { mutableStateOf("") }

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
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Row {
            OutlinedTextField(
                value = workoutName,
                onValueChange = { workoutName = it },
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, bottom = 15.dp)
                    .weight(4f),
                label = { Text("Workout Name") }
            )

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(top = 8.dp, end = 8.dp)
                    .weight(1.5f)
            ) {
                Text(text = "Done")
            }
        }

        Card(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            viewModel.exercises.forEachIndexed { index, exercise -> 
                ExerciseBox(exercise = exercise, exerciseIndex = index, viewModel = viewModel)
            }

            Button(
                onClick = { showDialog = true },
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Add Exercise")
            }

            if (showDialog) {
                ExerciseSelectionDialog(
                    exerciseList = exerciseList,
                    onExerciseSelected = { exercise ->
                        viewModel.addExercise(exercise)
                        showDialog = false
                    },
                    onDismiss = { showDialog = false }
                )
            }
        }
    }

}

@Composable
fun ExerciseBox(
    exercise: Exercise,
    exerciseIndex: Int,
    viewModel: WorkoutViewModel
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(
                text = exercise.name,
                modifier = Modifier
                    .weight(5f)
            )

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(3.dp)
            ) {
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "Choose sets")
            }

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(3.dp)
                    .size(50.dp, 50.dp)
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Exercise")
            }
        }
    }
}

@Preview
@Composable
fun WorkoutScreenPreview(
    navController: NavController = rememberNavController()
) {
    WorkoutScreen(navController = navController, viewModel = WorkoutViewModel())
}
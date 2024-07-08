package com.strongclone.app

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(
    modifier: Modifier,
    viewModel: NewViewModel = viewModel(),
    navController: NavController
) {
    val workouts by viewModel.workouts.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val context = LocalContext.current
        Text(
            text = "Ready to Start?",
            fontSize = 30.sp,
            modifier = Modifier.padding(30.dp)
        )

        Button(
            onClick = {
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 20.dp)
        ) {
            Text("Start Workout", fontSize = 20.sp, color = Color.White)
        }

        Text(
            text = "My Workouts",
            fontSize = 25.sp,
            modifier = Modifier.padding(horizontal = 30.dp, vertical = 10.dp)
        )

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 25.dp, vertical = 10.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(BorderStroke(2.dp, Color.Black), RoundedCornerShape(16.dp))
                .padding(8.dp)
        ) {
            items(workouts) { workout ->
                Text(
                    text = workout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    fontSize = 18.sp
                )
            }
        }

        Button(
            onClick = {
                navController.navigate("newWorkoutTemplate")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 10.dp)
        ) {
            Text(
                text = "Create Workout",
                fontSize = 20.sp
            )
        }
    }
}


@Preview(showBackground = false)
@Composable
fun PreviewNewScreen() {
    HomeScreen(modifier = Modifier, navController = rememberNavController())
}
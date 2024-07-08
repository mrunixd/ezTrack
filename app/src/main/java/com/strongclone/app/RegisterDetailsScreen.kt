package com.strongclone.app

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun RegisterDetailsScreen(
    viewModel: AuthViewModel = viewModel(),
    navController: NavController,
) {
    val context = LocalContext.current
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var sex by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var sexExpanded by remember { mutableStateOf(false) }
    val sexOptions = listOf("Male", "Female", "Non-Binary")

    val year = remember { mutableIntStateOf(2000) }
    val month = remember { mutableIntStateOf(0) }
    val day = remember { mutableIntStateOf(1) }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            year.intValue = selectedYear
            month.intValue = selectedMonth
            day.intValue = selectedDay
            dob = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        },
        year.intValue,
        month.intValue,
        day.intValue
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
    ) {
        Text(
            text = "Tell me more about you!",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            textAlign = TextAlign.Center
        )

        HorizontalDivider(
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 15.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name") },
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp),
            )

            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name") },
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp),
            )
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)) {
            OutlinedTextField(
                value = sex,
                onValueChange = {},
                label = { Text("Sex") },
                readOnly = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { sexExpanded = true },
            )
            DropdownMenu(
                expanded = sexExpanded,
                onDismissRequest = { sexExpanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                sexOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            sex = option
                            sexExpanded = false
                    })
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = height,
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) {
                        height = it
                    }
                },
                label = { Text("Height (cm)") },
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = weight,
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) {
                        weight = it
                    }
                },
                label = { Text("Weight (kg)") },
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        OutlinedTextField(
            value = dob,
            onValueChange = { },
            label = { Text("Date of Birth") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 5.dp)
                .clickable { datePickerDialog.show() },
            readOnly = true
        )

        Button(
            onClick = {
                if (firstName.isNotBlank() && lastName.isNotBlank() && height.isNotBlank() &&
                    weight.isNotBlank() && sex.isNotBlank() && dob.isNotBlank()
                ) {
                    viewModel.addUserToDB(firstName, lastName,sex, height, weight, dob)
                    navController.navigate("home")
                } else {
                    Toast.makeText(
                        context,
                        "Please fill all the fields!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Next")
        }
    }
}


@Preview
@Composable
fun RegisterDetailsScreenPreview() {
    RegisterDetailsScreen(
        navController = rememberNavController(),
    )
}

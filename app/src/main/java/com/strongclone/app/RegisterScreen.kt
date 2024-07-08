package com.strongclone.app

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@SuppressLint("ShowToast")
@Composable
fun RegisterScreen(
    modifier: Modifier,
    viewModel: AuthViewModel = AuthViewModel(),
    navController: NavController,
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val context = LocalContext.current

    val authState = viewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when(authState.value) {
            is AuthState.Authenticated -> navController.navigate("registerDetails")
            is AuthState.Error -> Toast.makeText(context, (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT)
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "EzTrack",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(vertical = 45.dp)
        )

        Card(
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Register",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    singleLine = true
                )

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    singleLine = true
                )

                Button(
                    onClick = {
                        if (password == confirmPassword) {
                            viewModel.signUp(email, password)
                        } else {
                            Toast.makeText(context, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = "Register",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Text(
                    text = "Already have account? Sign In",
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .align(Alignment.CenterHorizontally)
                        .clickable {
                            navController.navigate("login")
                        }
                )
            }
        }
    }
}

@Preview
@Composable
fun RegisterScreenPreview() {
    RegisterDetailsScreen(navController = rememberNavController())
}
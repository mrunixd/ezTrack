package com.strongclone.app

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel(),
    navController: NavController
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
    ) {
        val context = LocalContext.current
        Button(
            onClick = {
                viewModel.logout()
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent);
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Log Out", fontSize = 25.sp)
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview(
    viewModel: SettingsViewModel = viewModel()
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        val context = LocalContext.current
        Button(
            onClick = {
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Log Out", fontSize = 25.sp)
        }
    }
}

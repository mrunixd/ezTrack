package com.strongclone.app

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel(),
    navController: NavController
) {
    val profileState = remember { mutableStateOf(ProfileState()) }
    val context = LocalContext.current
    // Fetch user's name when the composable is first launched
    LaunchedEffect(key1 = true) {
        viewModel.getName(
            onSuccess = { fullName ->
                profileState.value = profileState.value.copy(name = fullName, isLoading = false)
            },
            onFailure = {
                profileState.value = profileState.value.copy(isLoading = false)
                // Handle failure case
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "My Profile",
            fontSize = 25.sp,
            modifier = Modifier
                .padding(horizontal = 25.dp, vertical = 15.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 10.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(BorderStroke(2.dp, Color.Black), RoundedCornerShape(16.dp))
                .padding(8.dp)
        )   {
            Image(
                painter = painterResource(id = R.drawable.round_circle_24),
                contentDescription = "Contact Profile Picture",
                modifier = Modifier
                    .size(75.dp)
            )
            if (profileState.value.isLoading) {
                Text(
                    text = "Loading...",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(start = 30.dp)
                )
            } else {
                Text(
                    text = profileState.value.name,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(start = 30.dp)
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 10.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(BorderStroke(2.dp, Color.Black), RoundedCornerShape(16.dp))
                .padding(15.dp)
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                ) {
                Text(
                    text = "Body Weight",
                    fontSize = 18.sp
                )
            }

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),

                ) {
                Text(
                    text = "Strength",
                    fontSize = 18.sp
                )
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),

                ) {
                Text(
                    text = "Consistency",
                    fontSize = 18.sp
                )
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),

                ) {
                Text(
                    text = "Personal Records",
                    fontSize = 18.sp
                )
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),

                ) {
                Text(
                    text = "BMI",
                    fontSize = 18.sp
                )
            }
        }

        Button(
            onClick = {
                val url = "https://www.github.com/mrunixd/ezTrack"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                context.startActivity(intent)
            },
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "EzTrack by mrunixd", fontSize = 12.sp)
        }
    }
}


@Preview
@Composable
fun ProfileScreenPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = "My Profile",
            fontSize = 30.sp,
            modifier = Modifier
                .padding(horizontal = 25.dp, vertical = 15.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 10.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(BorderStroke(2.dp, Color.White), RoundedCornerShape(16.dp))
                .padding(15.dp)
        )   {
            Image(
                painter = painterResource(id = R.drawable.round_circle_24),
                contentDescription = "Contact Profile Picture",
                modifier = Modifier
                    .size(75.dp)
            )
            Text(
                text = "John Doe",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(start = 30.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 10.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(BorderStroke(2.dp, Color.White), RoundedCornerShape(16.dp))
                .padding(15.dp)
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),

            ) {
                Text(
                    text = "Body Weight",
                    fontSize = 18.sp
                )
            }

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),

                ) {
                Text(
                    text = "Strength",
                    fontSize = 18.sp
                )
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),

                ) {
                Text(
                    text = "Consistency",
                    fontSize = 18.sp
                )
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),

                ) {
                Text(
                    text = "Personal Records",
                    fontSize = 18.sp
                )
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),

                ) {
                Text(
                    text = "BMI",
                    fontSize = 18.sp
                )
            }
        }

        Button(
            onClick = {

            },
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "EzTrack by mrunixd", fontSize = 12.sp)
        }
    }
}
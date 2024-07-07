package com.strongclone.app

import com.strongclone.app.NewScreen
import com.strongclone.app.NewWorkoutScreen
import com.strongclone.app.ProfileScreen
import com.strongclone.app.SettingsScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import java.util.Locale

@Preview
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val items = listOf("Profile", "New", "Settings")

    Column {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            NavigationComponent(navController)
        }

        BottomNavigationBar(navController, items)
    }
}

@Composable
fun NavigationComponent(navController: NavController) {
    NavHost(navController as NavHostController, startDestination = "new") {
        composable("new") { NewScreen(navController = navController) }
        composable("profile") { ProfileScreen(navController = navController) }
        composable("settings") { SettingsScreen(navController = navController) }
        composable("newWorkoutTemplate") { NewWorkoutScreen(navController = navController)}
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, items: List<String>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar(
        modifier = Modifier
            .padding(bottom = 0.dp)
    ) {
        items.forEach { screen ->
            NavigationBarItem(
                icon = {},
                label = { Text(screen.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }) },
                selected = currentRoute == screen,
                onClick = {
                    navController.navigate(screen) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}

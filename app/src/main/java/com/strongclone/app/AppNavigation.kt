package com.strongclone.app

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import java.util.Locale

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            if (currentRoute in listOf("home", "profile", "settings")) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen(modifier, navController = navController) }
            composable("profile") { ProfileScreen(navController = navController) }
            composable("settings") { SettingsScreen(navController = navController) }
            composable("newWorkoutTemplate") { NewWorkoutScreen(navController = navController) }
            composable("login") { LoginScreen(modifier, navController = navController) }
            composable("register") { RegisterScreen(modifier, navController = navController) }
            composable("registerDetails") { RegisterDetailsScreen(navController = navController) }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        "home" to Icons.Default.Home,
        "profile" to Icons.Default.Person,
        "settings" to Icons.Default.Settings
    )

    NavigationBar {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { (route, icon) ->
            NavigationBarItem(
                icon = { Icon(imageVector = icon, contentDescription = null) },
                label = { Text(route.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }) },
                selected = currentRoute == route,
                onClick = {
                    if (currentRoute != route) {
                        navController.navigate(route) {
                            // Avoid multiple copies of the same destination when reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                            // Pop up to the start destination of the graph to avoid building up a large stack of destinations on the back stack
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                        }
                    }
                }
            )
        }
    }
}
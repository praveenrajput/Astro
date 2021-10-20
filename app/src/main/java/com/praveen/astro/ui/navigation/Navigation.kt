package com.praveen.astro.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.praveen.astro.AstrosList
import com.praveen.astro.IssLocation
import com.praveen.astro.viewModels.AstrosViewModel

@Composable
fun Navigation(navController: NavHostController, astrosViewModel: AstrosViewModel) {
    NavHost(navController = navController, startDestination = "astros") {
        composable("astros") {
            AstrosList(astrosViewModel = astrosViewModel)
        }

        composable("issLocation") {
            IssLocation(astrosViewModel = astrosViewModel)
        }
    }
}

package com.praveen.astro.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.praveen.astro.AstrosList
import com.praveen.astro.IssLocation
import com.praveen.astro.ui.details.AstroDetail
import com.praveen.astro.viewModels.AstrosViewModel

@Composable
fun Navigation(
    paddingValues: PaddingValues,
    navController: NavHostController,
    astrosViewModel: AstrosViewModel
) {
    NavHost(navController = navController, startDestination = "astros") {
        composable("astros") {
            AstrosList(
                paddingValues = paddingValues,
                astrosViewModel = astrosViewModel,
                onItemClick = {
                    navController.navigate("astrosDetails/$it")
                }
            )
        }

        composable("issLocation") {
            IssLocation(
                paddingValues = paddingValues,
                astrosViewModel = astrosViewModel
            )
        }

        composable(
            "astrosDetails/{astroName}",
            arguments = listOf(navArgument("astroName") { type = NavType.StringType })
        ) {
            val astroName = it.arguments?.getString("astroName")
            astroName?.let {
                AstroDetail(
                    astroName = astroName,
                    astrosViewModel = astrosViewModel
                )
            }
        }
    }
}

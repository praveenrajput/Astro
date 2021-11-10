package com.praveen.astro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.praveen.astro.models.BottomNavItem
import com.praveen.astro.models.IssNow
import com.praveen.astro.ui.astros.AstroItem
import com.praveen.astro.ui.common.ScreenTitle
import com.praveen.astro.ui.issPosition.IssDetails
import com.praveen.astro.ui.issPosition.MapView
import com.praveen.astro.ui.navigation.Navigation
import com.praveen.astro.ui.theme.AstroTheme
import com.praveen.astro.utils.AstroScreen
import com.praveen.astro.viewModels.AstrosViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val astrosViewModel: AstrosViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AstroTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        if (
                            currentRoute(navController) == "issLocation" ||
                            currentRoute(navController) == "astros"
                        ) {
                            BottomNavigationBar(
                                items = listOf(
                                    BottomNavItem(
                                        name = "Astros",
                                        route = "astros",
                                        icon = Icons.Default.Person,
                                        popUpto = "issLocation"
                                    ),
                                    BottomNavItem(
                                        name = "ISS Location",
                                        route = "issLocation",
                                        icon = Icons.Default.LocationOn,
                                        popUpto = "astros"
                                    )
                                ),
                                navController = navController,
                                onItemClick = {
                                    navController.navigate(it.route) {
                                        launchSingleTop = true
                                        popUpTo(it.popUpto) { inclusive = true }
                                    }
                                }
                            )
                        }
                    }
                ) {
                    Navigation(
                        paddingValues = it,
                        navController = navController,
                        astrosViewModel = astrosViewModel,
                    )
                }
            }
        }
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.White,
        elevation = 4.dp
    ) {
        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.name
                    )
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Gray
            )
        }
    }
}

@Composable
fun AstrosList(
    paddingValues: PaddingValues,
    astrosViewModel: AstrosViewModel,
    onItemClick: (String) -> Unit
) {
    val astrosList by astrosViewModel.peopleLiveData.observeAsState(listOf())
    Column(
        modifier = Modifier.padding(paddingValues)
    ) {
        ScreenTitle(
            title = AstroScreen.Astros.title,
            modifier = Modifier
                .padding(20.dp)
        )
        LazyColumn {
            items(astrosList) {
                AstroItem(
                    people = it,
                    onItemClick = onItemClick
                )
            }
        }
    }
}

@Composable
fun IssLocation(
    paddingValues: PaddingValues,
    astrosViewModel: AstrosViewModel
) {
    val issNow by astrosViewModel.issNowLiveData.observeAsState(IssNow())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Center
    ) {
        ScreenTitle(
            title = AstroScreen.IssPosition.title,
            modifier = Modifier
                .padding(20.dp)

        )
        IssDetails(issNow = issNow)
        MapView(issNow.issPosition)
    }
}

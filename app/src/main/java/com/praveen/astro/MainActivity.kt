package com.praveen.astro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.LatLng
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import com.praveen.astro.location.rememberMapViewWithLifecycle
import com.praveen.astro.models.BottomNavItem
import com.praveen.astro.models.IssNow
import com.praveen.astro.models.IssPosition
import com.praveen.astro.models.People
import com.praveen.astro.ui.navigation.Navigation
import com.praveen.astro.ui.theme.AstroTheme
import com.praveen.astro.utils.getFormattedTime
import com.praveen.astro.viewModels.AstrosViewModel
import kotlinx.coroutines.launch
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
                        BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    name = "Astros",
                                    route = "astros",
                                    icon = Icons.Default.Person
                                ),
                                BottomNavItem(
                                    name = "ISS Location",
                                    route = "issLocation",
                                    icon = Icons.Default.LocationOn
                                )
                            ),
                            navController = navController,
                            onItemClick = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                ) {
                    Navigation(
                        navController = navController,
                        astrosViewModel = astrosViewModel
                    )
                }
            }
        }
    }
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
        backgroundColor = Color.Gray,
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
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.White
            )
        }
    }
}

@Composable
fun AstrosList(astrosViewModel: AstrosViewModel) {
    val astrosList by astrosViewModel.peopleLiveData.observeAsState(listOf())
    LazyColumn {
        items(astrosList) {
            AstrosDetail(people = it)
        }
    }
}

@Composable
fun AstrosDetail(people: People) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = people.name,
            textAlign = TextAlign.Center
        )
        Text(
            text = people.craft,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun IssLocation(astrosViewModel: AstrosViewModel) {
    val issNow by astrosViewModel.issNowLiveData.observeAsState(IssNow())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp),
        verticalArrangement = Arrangement.Center
    ) {
        IssDetails(issNow = issNow)
        MapView(issNow.issPosition)
    }
}

@Composable
fun IssDetails(
    issNow: IssNow
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = issNow.issPosition.latitude,
            textAlign = TextAlign.Center
        )
        Text(
            text = issNow.issPosition.longitude,
            textAlign = TextAlign.Center
        )
        Text(
            text = getFormattedTime(issNow.timestamp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun MapView(
    position: IssPosition
) {
    val mapView = rememberMapViewWithLifecycle()
    MapViewContainer(map = mapView, issPosition = position)
}

@Composable
private fun MapViewContainer(
    map: MapView,
    issPosition: IssPosition
) {
    var mapState by remember(map) { mutableStateOf(false) }

    val position = remember(issPosition) {
        LatLng(issPosition.latitude.toDouble(), issPosition.longitude.toDouble())
    }

    LaunchedEffect(map, mapState) {
        if (!mapState) {
            map.awaitMap()
            mapState = true
        }
    }
    val coroutineScope = rememberCoroutineScope()
    AndroidView({ map }) { mapView ->
        coroutineScope.launch {
            val googleMap = mapView.awaitMap()
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 3f))

            googleMap.addMarker {
                title("ISS")
                position(position)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AstroTheme {
        AstrosDetail(people = People("name", "ii"))
    }
}

package com.praveen.astro.ui.issPosition

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.LatLng
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap
import com.praveen.astro.location.rememberMapViewWithLifecycle
import com.praveen.astro.misc.IssDetailTag
import com.praveen.astro.misc.IssPositionKey
import com.praveen.astro.misc.MapViewTag
import com.praveen.astro.models.IssNow
import com.praveen.astro.models.IssPosition
import com.praveen.astro.utils.getFormattedTime
import kotlinx.coroutines.launch

val IssPositionSemanticKey = SemanticsPropertyKey<IssPosition>(IssPositionKey)
var SemanticsPropertyReceiver.currentIssPosition by IssPositionSemanticKey

@Composable
fun IssDetails(
    issNow: IssNow
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(10.dp)
            .testTag(IssDetailTag)
    ) {
        val (lat, lon, lastUpdated, latValue, lonValue, lastUpdatedValue) = createRefs()
        createHorizontalChain(lat, lon, lastUpdated, chainStyle = ChainStyle.Spread)
        Text(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(4.dp)
                .constrainAs(lat) {},
            text = "Latitude",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(4.dp)
                .constrainAs(lon) {
                    start.linkTo(lat.end)
                    centerVerticallyTo(lat)
                },
            text = "Longitude",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(4.dp)
                .constrainAs(lastUpdated) {
                    start.linkTo(lon.end)
                    centerVerticallyTo(lat)
                },
            text = "Last updated",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(4.dp)
                .constrainAs(latValue) {
                    centerHorizontallyTo(lat)
                    top.linkTo(lat.bottom)
                },
            text = issNow.issPosition.latitude,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(4.dp)
                .constrainAs(lonValue) {
                    centerHorizontallyTo(lon)
                    top.linkTo(lon.bottom)
                },
            text = issNow.issPosition.longitude,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .padding(4.dp)
                .constrainAs(lastUpdatedValue) {
                    centerHorizontallyTo(lastUpdated)
                    top.linkTo(lastUpdated.bottom)
                },
            text = getFormattedTime(issNow.timestamp),
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MapView(
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
    AndroidView(
        { map },
        modifier = Modifier
            .testTag(MapViewTag)
            .semantics { currentIssPosition = issPosition }
    ) { mapView ->
        coroutineScope.launch {
            val googleMap = mapView.awaitMap()
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 3f))

            googleMap.clear()
            googleMap.addMarker {
                title("ISS")
                position(position)
            }
        }
    }
}

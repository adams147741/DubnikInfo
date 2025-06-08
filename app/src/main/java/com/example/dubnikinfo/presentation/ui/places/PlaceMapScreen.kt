package com.example.dubnikinfo.presentation.ui.places

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.dubnikinfo.presentation.ui.TopBar.TopBar
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PointOfInterest
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.compose.material3.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.dubnikinfo.R
import com.example.dubnikinfo.utils.LatLngSaver
import com.google.android.gms.maps.CameraUpdateFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PlaceMapScreen(
    viewModel: PlaceViewModel,
    onBackClick: () -> Unit
) {
    val coroutine: CoroutineScope = rememberCoroutineScope()
    val placeList by viewModel.places.collectAsState()
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(47.9571, 18.4111), 15f)
    }
    var selectedPOI by remember { mutableStateOf<PointOfInterest?>(null) }
    var selectedPlace by rememberSaveable(stateSaver = LatLngSaver) { mutableStateOf<LatLng?>(null) }

    Scaffold(
        topBar = {
            TopBar(title = stringResource(R.string.map), onNavigationClick = onBackClick)
        },
        content = { innerPadding ->
            Column (
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                GoogleMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.5f),
                    cameraPositionState = cameraPositionState,
                    onPOIClick = { selectedPOI = it }
                ) {
                    selectedPOI?.let { poi ->
                        Marker(
                            state = MarkerState(position = poi.latLng),
                            title = poi.name,
                        )
                    }
                    selectedPlace?.let { place ->
                        Marker(
                            state = MarkerState(position = LatLng(place.latitude, place.longitude)),
                            title = placeList.firstOrNull() {it.latitude == place.latitude && it.longitude == place.longitude}?.name,
                            snippet = placeList.firstOrNull() {it.latitude == place.latitude && it.longitude == place.longitude}?.description
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text (
                        text = stringResource(R.string.places),
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    items(placeList) { place ->
                        placeCard(
                            place.name,
                            place.description,
                            onCardClick = {
                                val latLng = LatLng(place.latitude, place.longitude)
                                coroutine.launch {
                                    cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(latLng, 17f))
                                }
                                selectedPlace = latLng
                            }
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun placeCard(
    name: String,
    description: String,
    onCardClick: () -> Unit
) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onCardClick() },
    ){
        Column (
            modifier = Modifier.padding(8.dp),
        ) {
            Text(
                text = name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = description,
                fontSize = 12.sp,
            )
        }
    }
}

@Composable
@Preview
fun placeCardPreview() {
    placeCard(name = "name", description = "description") { }
}
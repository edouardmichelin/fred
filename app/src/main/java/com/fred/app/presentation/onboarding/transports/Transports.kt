package com.fred.app.presentation.onboarding.transports

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.fred.app.R
import com.fred.app.data.repository.model.FuelType
import com.fred.app.data.repository.model.User
import com.fred.app.data.repository.model.Vehicle
import com.fred.app.data.repository.model.VehicleType
import kotlinx.coroutines.flow.collect

@Composable
fun TransportationSurveyScreen(
    onSubmit: () -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: TransportsViewModel = hiltViewModel()
) {
    // States for Car Usage
    var ownsCar by remember { mutableStateOf(false) }
    var carType by remember { mutableStateOf("") }
    var kilometersDriven by remember { mutableStateOf(0f) }
    var vehicleAge by remember { mutableStateOf(0f) }

    // States for Public Transport
    var usesPublicTransport by remember { mutableStateOf(false) }
    var publicTransportType by remember { mutableStateOf("") }
    var publicTransportFrequency by remember { mutableStateOf("") }
    var usesBike by remember { mutableStateOf(false) }
    var walkingFrequency by remember { mutableStateOf("") }

    // States for Flights
    var flightsTaken by remember { mutableStateOf(0) }
    var flightDistance by remember { mutableStateOf("") }

    var submitted by remember { mutableStateOf(false) }

    val vehicleCount = viewModel.vehicleCount.collectAsState()
    if (submitted) {
        viewModel.createdCount.asLiveData().observeForever {
            if (it == vehicleCount.value) {
                navController.navigate("energy")
            }
        }
    }

    // plane
    viewModel.incrementVehicleCount()

    // jambes
    viewModel.incrementVehicleCount()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        // Title
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.fred_no_bg_removebg_preview), // Replace with your image resource
                contentDescription = "Transportation Icon",
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Transportation Survey",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Car Usage Section
        Text(
            text = "Car Usage",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Do you own a car?",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium
            )
            Switch(
                checked = ownsCar,
                onCheckedChange = {
                    ownsCar = it
                    if (it) {
                        viewModel.incrementVehicleCount()
                    } else {
                        viewModel.decrementVehicleCount()
                    }
                }
            )
        }
        if (ownsCar) {
            Spacer(modifier = Modifier.height(16.dp))
            Divider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text("Car Fuel")
            ChipGroup(
                options = FuelType.values().map { it.name },
                selectedOption = carType,
                onOptionSelected = { carType = it }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text("How many kilometers/miles do you drive per week?")
            Slider(
                value = kilometersDriven,
                onValueChange = { kilometersDriven = it },
                valueRange = 0f..1000f,
                modifier = Modifier.fillMaxWidth()
            )
            Text(text = "${kilometersDriven.toInt()} km")

            Spacer(modifier = Modifier.height(16.dp))

            Text("How old is your vehicle?")
            Slider(
                value = vehicleAge,
                onValueChange = { vehicleAge = it },
                valueRange = 0f..50f, // Assuming a maximum age of 50 years
                steps = 49,
                modifier = Modifier.fillMaxWidth()
            )
            Text(text = "${vehicleAge.toInt()} years old")
        }

        Spacer(modifier = Modifier.height(24.dp))
        Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f))
        Spacer(modifier = Modifier.height(24.dp))

        // Public Transport Section
        Text(
            text = "Public Transport",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Do you use public transport regularly?",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium
            )
            Switch(
                checked = usesPublicTransport,
                onCheckedChange = {
                    usesPublicTransport = it
                    if (it) {
                        viewModel.incrementVehicleCount()
                    } else {
                        viewModel.decrementVehicleCount()
                    }
                }
            )
        }
        if (usesPublicTransport) {
            Spacer(modifier = Modifier.height(16.dp))
            Divider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text("How often?")
            ChipGroup(
                options = listOf("Daily", "Weekly", "Monthly"),
                selectedOption = publicTransportFrequency,
                onOptionSelected = { publicTransportFrequency = it }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Bike Usage Section
        Text(
            text = "Bike Usage",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Do you use a bike?",
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyMedium
            )
            Switch(
                checked = usesBike,
                onCheckedChange = {
                    usesBike = it
                    if (it) {
                        viewModel.incrementVehicleCount()
                    } else {
                        viewModel.decrementVehicleCount()
                    }
                }
            )
        }

        Divider(thickness = 2.dp, color = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f))
        Spacer(modifier = Modifier.height(24.dp))

        // Flights Section
        Text(
            text = "Flights",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text("How many flights did you take in the past year?")
        Slider(
            value = flightsTaken.toFloat(),
            onValueChange = { flightsTaken = it.toInt() },
            valueRange = 0f..7f,
            steps = 6,
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = "$flightsTaken flights")

        Spacer(modifier = Modifier.height(16.dp))

        Text("What was the typical distance?")
        ChipGroup(
            options = listOf(
                "< 500 km",
                "500–3000 km",
                ">3000 km"
            ),
            selectedOption = flightDistance,
            onOptionSelected = {
                flightDistance = it
            }
        )

        // Submit Button
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
            }

            Button(
                onClick = {
                    if (ownsCar) {
                        viewModel.createVehicle(
                            type = VehicleType.Car,
                            name = "My Car",
                            fuelType = FuelType.values().firstOrNull { it.name == carType }
                                ?: FuelType.Diesel,
                            age = vehicleAge.toInt(),
                            km = kilometersDriven.toInt(),
                            carbonFootprint = 1000.0
                        )
                    }

                    if (usesBike) {
                        viewModel.createVehicle(
                            type = VehicleType.Bike,
                            name = "My Bike",
                            fuelType = FuelType.Muscle,
                            age = vehicleAge.toInt(),
                            km = kilometersDriven.toInt(),
                            carbonFootprint = 0.0
                        )
                    }

                    if (usesPublicTransport) {
                        viewModel.createVehicle(
                            type = VehicleType.PublicTransport,
                            name = "Public Transport",
                            fuelType = FuelType.Electric,
                            age = vehicleAge.toInt(),
                            km = kilometersDriven.toInt(),
                            carbonFootprint = 0.0
                        )
                    }

                    viewModel.createVehicle(
                        type = VehicleType.Plane,
                        name = "Flight",
                        fuelType = FuelType.Gas,
                        age = 0,
                        km = 0,
                        carbonFootprint = 1000.0
                    )

                    viewModel.createVehicle(
                        type = VehicleType.Walk,
                        name = "Legs",
                        fuelType = FuelType.Muscle,
                        age = 0,
                        km = 0,
                        carbonFootprint = 0.0
                    )

                    submitted = true
                    navController.navigate("energy")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter) // Now valid within BoxScope
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Next",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                        contentDescription = "Next Icon",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun ChipGroup(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        options.forEach { option ->
            FilterChip(
                selected = selectedOption == option,
                onClick = { onOptionSelected(option) },
                label = { Text(option) },
                colors = FilterChipDefaults.filterChipColors(
                )
            )
        }
    }
}

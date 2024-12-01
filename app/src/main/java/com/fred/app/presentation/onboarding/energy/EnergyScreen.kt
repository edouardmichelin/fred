package com.fred.app.presentation.onboarding.energy

import android.content.pm.PackageManager
import android.location.Geocoder
import android.util.Log
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.fred.app.R
import com.fred.app.data.repository.model.Activity
import com.google.android.gms.location.LocationServices
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnergyOnboardingScreen(onSubmit: () -> Unit, navController: NavController) {

  var locationAccessGranted by remember { mutableStateOf(false) }
  var selectedCountry by remember { mutableStateOf("") }
  val countries = listOf("USA", "Canada", "France", "Germany", "Japan")
  var energy by remember { mutableStateOf(0) }

  fun fetchEnergyData(country: String): Int {
    val hardcodedEnergyData =
        mapOf(
            "USA" to 1000,
            "Canada" to 800,
            "France" to 500,
            "Germany" to 600,
            "Japan" to 700,
            "Switzerland" to 380,
            "CH" to 380,
            "Unknown" to -1000)
    return hardcodedEnergyData.get(country) ?: -1000
  }

  var showCountrySelection by remember { mutableStateOf(false) }

  // Fetch the current country when location access is granted
  if (locationAccessGranted) {
    getCurrentCountry { detectedCountry ->
      selectedCountry = detectedCountry
      Log.d("EnergyOnboardingScreen", "Detected country: $detectedCountry")
      energy = fetchEnergyData(detectedCountry)
    }
  }

  Column(
      modifier = Modifier.fillMaxSize().padding(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally) {
        // Title Section
        Row(verticalAlignment = Alignment.CenterVertically) {
          Image(
              painter =
                  painterResource(
                      id =
                          R.drawable
                              .fred_no_bg_removebg_preview), // Replace with your image resource
              contentDescription = "Energy Icon",
              modifier = Modifier.size(48.dp))
          Spacer(modifier = Modifier.width(8.dp))
          Text(
              text = "Energy",
              style = MaterialTheme.typography.headlineMedium,
              color = MaterialTheme.colorScheme.primary)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text =
                "We need to fetch your energy consumption details. Please allow location access or select a country.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(24.dp))

        // Location Permission Section
        if (!locationAccessGranted) {
          Text(
              text = "Do you allow location access to get energy data?",
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onBackground)
          Spacer(modifier = Modifier.height(16.dp))

          Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                  locationAccessGranted = true
                  energy = fetchEnergyData("Switzerland")
                },
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary)) {
                  Text("Allow Location")
                }
            Button(
                onClick = { locationAccessGranted = false },
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary)) {
                  Text("Deny Location")
                }
          }
        } else {
          Text(
              text = "Location access granted. Thanks!",
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onBackground)
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (!locationAccessGranted) {
          Text(
              text = "Select your country to get energy data:",
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onBackground)
          Spacer(modifier = Modifier.height(16.dp))

          OutlinedButton(
              onClick = { showCountrySelection = true }, modifier = Modifier.fillMaxWidth()) {
                Text(text = if (selectedCountry.isNotEmpty()) selectedCountry else "Select Country")
              }
        }

        Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
          Column(
              modifier = Modifier.fillMaxSize().padding(16.dp),
              horizontalAlignment = Alignment.CenterHorizontally) {}

          Button(
              onClick = {
                Log.d("EnergyOnboardingScreen", "Energy: $energy")
                onSubmit()
                navController.navigate("home")
              },
              modifier =
                  Modifier.fillMaxWidth()
                      .align(Alignment.BottomCenter) // Now valid within BoxScope
                      .padding(16.dp),
              colors =
                  ButtonDefaults.buttonColors(
                      containerColor = MaterialTheme.colorScheme.secondaryContainer,
                      contentColor = MaterialTheme.colorScheme.onSecondaryContainer)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(text = "Next", style = MaterialTheme.typography.bodyMedium)
                  Spacer(modifier = Modifier.width(8.dp))
                  androidx.compose.material3.Icon(
                      imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                      contentDescription = "Next Icon",
                      modifier = Modifier.size(20.dp))
                }
              }
        }
      }

  // Modal Bottom Sheet for Country Selection
  if (showCountrySelection) {
    ModalBottomSheet(onDismissRequest = { showCountrySelection = false }) {
      Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Select Country",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp))
        countries.forEach { country ->
          TextButton(
              onClick = {
                selectedCountry = country
                showCountrySelection = false
                energy = fetchEnergyData(country)
              },
              modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = country,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface)
              }
        }
      }
    }
  }
}

@Composable
fun getCurrentCountry(onCountryDetected: (String) -> Unit) {
  val context = LocalContext.current
  val fusedLocationProviderClient = remember {
    LocationServices.getFusedLocationProviderClient(context)
  }

  LaunchedEffect(Unit) {
    if (ContextCompat.checkSelfPermission(
        context, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
        PackageManager.PERMISSION_GRANTED)
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
          if (location != null) {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            if (!addresses.isNullOrEmpty()) {
              val country = addresses[0].countryName
              onCountryDetected(country)
            } else {
              onCountryDetected("Unknown")
            }
          } else {
            onCountryDetected("Unknown")
          }
        }
    else {
      ActivityCompat.requestPermissions(
          context as android.app.Activity,
          arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
          1)
    }
  }
}

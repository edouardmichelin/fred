package com.fred.app.presentation.onboarding.splash

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fred.app.R

@Composable
fun SplashScreen(navController: NavController) {
  Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
    // Centered content
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
          // Image of the character
          Image(
              painter =
                  painterResource(
                      id =
                          R.drawable
                              .fred_no_bg_removebg_preview), // Replace with your image resource
              contentDescription = "Fred Image",
              modifier = Modifier.size(280.dp),
              contentScale = ContentScale.Fit)

          Spacer(modifier = Modifier.height(64.dp))

          // Text
          Text(
              text =
                  "Hi! My name is Fred. Together, we will reduce your Carbon Footprint! 🌍\nI just need you to answer some questions first, but it will not be long, I promise! 🤞🏼",
              style = MaterialTheme.typography.headlineSmall,
              modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp))
        }

    // Next button at the bottom
    Button(
        onClick = { navController.navigate("personal") },
        modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).padding(16.dp),
        colors =
            ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer)) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Next", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.ArrowForward,
                contentDescription = "Next Icon",
                modifier = Modifier.size(20.dp))
          }
        }
  }
}

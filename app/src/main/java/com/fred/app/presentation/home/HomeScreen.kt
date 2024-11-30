package com.fred.app.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fred.app.R
import com.fred.app.ui.component.DefaultScaffold

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navigateToProfile: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()

    DefaultScaffold(
        topBar = { }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Ajouter un padding pour éviter le chevauchement
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Hello François!",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {
                            navigateToProfile()
                        }
                )
            }


            CarbonFootprintSuggestions(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

        }
    }
}


@Composable
fun CarbonFootprintSuggestions(modifier: Modifier = Modifier) {
    val suggestions = listOf(
        "Use public transport or carpool when possible.",
        "Reduce energy consumption by turning off unused lights.",
        "Avoid single-use plastics and use reusable items.",
        "Eat locally-sourced and plant-based meals.",
        "Recycle and compost your waste whenever possible."
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(24.dp)
            )
            .border(
                width = 1.2.dp,
                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.12f),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Today's Green Tips",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Here are some simple steps to help reduce your carbon footprint:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                suggestions.forEach { suggestion ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Suggestion",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = suggestion,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            // Décorative image qui dépasse volontairement les limites
            Box(
                modifier = Modifier
                    .size(100.dp) // Ajuster la taille de l'image
                    .offset(x = 24.dp, y = 16.dp) // Décalage pour faire dépasser l'image
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fred_no_bg_removebg_preview), // Remplace par ton drawable
                    contentDescription = "Decorative Leaf",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}




@Composable
fun LivingRoomTaskCard() {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(16.dp), RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Living Room",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color(0xFFA4C639), // Light green color
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                TaskItem(
                    taskTitle = "Water",
                    taskDescription = "hoya australis",
                    iconTint = Color(0xFFA4C639)
                )

                Spacer(modifier = Modifier.height(8.dp))

                TaskItem(
                    taskTitle = "Feed",
                    taskDescription = "monstera siltepecana",
                    iconTint = Color(0xFFA4C639)
                )
            }

            Image(
                painter = painterResource(id = R.drawable.fred_no_bg_removebg_preview), // Replace with your leaf drawable
                contentDescription = "Decorative Leaf",
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun TaskItem(taskTitle: String, taskDescription: String, iconTint: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle, // Replace with your preferred icon
            contentDescription = "Task Icon",
            tint = iconTint,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = taskTitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
            Text(
                text = taskDescription,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Gray
                )
            )
        }
    }
}


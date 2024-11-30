package com.fred.app.presentation.home

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
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

            ScoreCard(
                score = 19921,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )


            CarbonFootprintSuggestions(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

        }
    }
}

@Composable
fun ScoreCard(score: Int, modifier: Modifier = Modifier) {
    val containerColor: Color = if (score > 10000) {
        MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.7f)
    } else {
        MaterialTheme.colorScheme.secondaryContainer
    }

    val onContainerColor: Color = if (score > 10000) {
        MaterialTheme.colorScheme.onErrorContainer
    } else {
        MaterialTheme.colorScheme.onSecondaryContainer
    }

    val borderColor: Color = if (score > 10000) {
        Color.Red.copy(alpha = 0.3f)
    } else {
        Color.Green.copy(alpha = 0.3f)
    }

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        border = BorderStroke(0.4.dp, borderColor),
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = containerColor,
            contentColor = onContainerColor,
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Today, your score is",
                style = MaterialTheme.typography.headlineSmall,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${score} Freddies",
                    style = MaterialTheme.typography.headlineLarge,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Try completing some tasks today to decrease your score!",
                style = MaterialTheme.typography.labelSmall,
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

    // State to track the checked status of each suggestion
    val checkedStates = remember { mutableStateListOf(*Array(suggestions.size) { false }) }

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
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Today's Green Tips",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )

                    Text(
                        text = "Here are some simple steps to help reduce your carbon footprint:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.fred_no_bg_removebg_preview),
                    contentDescription = "Decorative Leaf",
                    modifier = Modifier
                        .size(64.dp)
                        .align(Alignment.Top),
                    contentScale = ContentScale.Fit
                )
            }

            suggestions.forEachIndexed { index, suggestion ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { 
                            checkedStates[index] = !checkedStates[index]
                        }
                        .padding(vertical = 8.dp)
                ) {
                    Checkbox(
                        checked = checkedStates[index],
                        onCheckedChange = null,
                        modifier = Modifier.size(32.dp),
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.primary,
                            uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            checkmarkColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                    Spacer(modifier = Modifier.width(12.dp)) // Increase spacing for larger checkbox
                    Column {
                        // Suggestion Text
                        Text(
                            text = suggestion,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        // Score Decrease Text
                        Text(
                            text = "Score: - 10 Freddies", // Replace with dynamic value if needed
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f) // Slightly faded color
                        )
                    }
                }
            }
        }
    }
}


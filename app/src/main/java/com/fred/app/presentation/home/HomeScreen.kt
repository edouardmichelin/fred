package com.fred.app.presentation.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fred.app.R
import com.fred.app.ui.component.DefaultScaffold

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToProfile: () -> Unit,
) {
    val suggestions by viewModel.suggestions.collectAsState()
    Log.d("HomeScreen", "Suggestions: $suggestions")

    DefaultScaffold(
        topBar = { }
    ) { innerPadding ->
        // Add vertical scrolling capability
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()) // Add scrolling behavior
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
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
                score = 9921,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            CarbonFootprintSuggestions(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            PersonalizedSuggestions(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }
    }
}


@Composable
fun PersonalizedSuggestions(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color(0xFFB78700).copy(alpha = 0.07f),
            contentColor = Color(0xFFB78700),
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp) // Add padding inside the card
        ) {
            Text(
                text = "Personalized Suggestions",
                style = MaterialTheme.typography.headlineSmall,
                color = Color(0xFFB78700),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            repeat(1) { // Example suggestions
                Text(
                    text = "Based on your location and the hour of the day, we suggest you to take a walk in the park.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFB78700)
                )
            }
        }
    }
}


@Composable
fun ScoreCard(score: Int, modifier: Modifier = Modifier) {
    val containerColor: Color = if (score > 10000) {
        MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
    } else {
        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
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
                    text = "\t ${score} Freddies",
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

    val checkedStates = remember { mutableStateListOf(*Array(suggestions.size) { false }) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.2f),
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
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = suggestion,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Score: - 10 Freddies",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f) // Slightly faded color
                        )
                    }
                }
            }
        }
    }
}


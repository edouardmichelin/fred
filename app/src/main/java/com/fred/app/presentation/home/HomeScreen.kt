package com.fred.app.presentation.home

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
import androidx.compose.material3.CircularProgressIndicator
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
import com.fred.app.data.repository.model.Suggestion
import com.fred.app.ui.component.DefaultScaffold

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToProfile: () -> Unit,
) {
  val isLoading by viewModel.isLoading.collectAsState()
  val user by viewModel.user.collectAsState()
  val suggestions by viewModel.suggestions.collectAsState()
  val plausibleActions by viewModel.plausibleActions.collectAsState()

  if (isLoading) {
    Box(modifier = Modifier.fillMaxWidth().padding(16.dp), contentAlignment = Alignment.Center) {
      IndeterminateCircularIndicator()
    }
    return
  }

  DefaultScaffold(topBar = {}) { innerPadding ->
    Column(
        modifier =
            Modifier.fillMaxSize().padding(innerPadding).verticalScroll(rememberScrollState())) {
          Row(
              modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Hello ${user?.username ?: ""}!",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.size(32.dp).clickable { navigateToProfile() })
              }

          ScoreCard(
              isLoading = user == null,
              score = user?.score ?: 0,
              modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp))

          CarbonFootprintSuggestions(
              title = "Today's Green Tips",
              description = "Here are some simple steps to help reduce your carbon footprint:",
              suggestions = suggestions,
              onCheck = { suggestion -> viewModel.completeSuggestion(suggestion = suggestion) },
              modifier =
                  Modifier.fillMaxWidth()
                      .padding(horizontal = 16.dp, vertical = 8.dp)
                      .background(
                          color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.2f),
                          shape = RoundedCornerShape(24.dp)))

          CarbonFootprintSuggestions(
              title = "Things you might wanna do now",
              description = "Based on your location and the time of the day.",
              suggestions = plausibleActions,
              onCheck = { suggestion -> viewModel.completeSuggestion(suggestion = suggestion) },
              modifier =
                  Modifier.fillMaxWidth()
                      .padding(horizontal = 16.dp, vertical = 8.dp)
                      .background(
                          color = MaterialTheme.colorScheme.secondaryContainer,
                          shape = RoundedCornerShape(24.dp)))
        }
  }
}

@Composable
fun IndeterminateCircularIndicator() {
  CircularProgressIndicator(
      modifier = Modifier.width(64.dp).padding(16.dp),
      color = MaterialTheme.colorScheme.secondary,
      trackColor = MaterialTheme.colorScheme.surfaceVariant,
  )
}

@Composable
fun PersonalizedSuggestions(plausibleActions: List<Suggestion>, modifier: Modifier = Modifier) {
  Card(
      modifier = modifier,
      shape = RoundedCornerShape(16.dp),
      colors =
          CardDefaults.elevatedCardColors(
              containerColor = Color(0xFFB78700).copy(alpha = 0.07f),
              contentColor = Color(0xFFB78700),
          )) {
        Column(
            modifier = Modifier.padding(16.dp) // Add padding inside the card
            ) {
              Text(
                  text = "",
                  style = MaterialTheme.typography.headlineSmall,
                  color = Color(0xFFB78700),
                  modifier = Modifier.padding(bottom = 8.dp))

              repeat(1) { // Example suggestions
                Text(
                    text = "",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFFB78700))
              }
            }
      }
}

@Composable
fun ScoreCard(isLoading: Boolean, score: Int, modifier: Modifier = Modifier) {

  val containerColor: Color =
      if (score > 10000) {
        MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
      } else {
        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
      }

  val onContainerColor: Color =
      if (score > 10000) {
        MaterialTheme.colorScheme.onErrorContainer
      } else {
        MaterialTheme.colorScheme.onSecondaryContainer
      }

  val borderColor: Color =
      if (score > 10000) {
        Color.Red.copy(alpha = 0.3f)
      } else {
        Color.Green.copy(alpha = 0.3f)
      }

  Card(
      modifier = modifier,
      shape = RoundedCornerShape(16.dp),
      colors =
          CardDefaults.elevatedCardColors(
              containerColor = containerColor,
              contentColor = onContainerColor,
          )) {
        if (isLoading) {
          Box(
              modifier = Modifier.fillMaxWidth().padding(16.dp),
              contentAlignment = Alignment.Center) {
                IndeterminateCircularIndicator()
              }
        } else {
          Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Your score is",
                style = MaterialTheme.typography.headlineSmall,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {
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
}

@Composable
fun CarbonFootprintSuggestions(
    title: String,
    description: String,
    suggestions: List<Suggestion>,
    modifier: Modifier = Modifier,
    onCheck: (Suggestion) -> Unit = {}
) {
  var checkedStates = remember { mutableStateListOf(*Array(suggestions.size) { false }) }

  //    if (suggestions.isEmpty()) {
  //        Box(
  //            modifier = Modifier
  //                .fillMaxWidth()
  //                .padding(16.dp),
  //            contentAlignment = Alignment.Center
  //        ) {
  //            IndeterminateCircularIndicator()
  //        }
  //        return
  //    }

  if (checkedStates.size != suggestions.size) {
    checkedStates = remember { mutableStateListOf(*Array(suggestions.size) { false }) }
  }

  Box(modifier = modifier.fillMaxWidth().padding(16.dp)) {
    Column {
      Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.Top) {
            Column(modifier = Modifier.weight(1f)) {
              Text(
                  text = title,
                  style = MaterialTheme.typography.headlineSmall,
                  color = MaterialTheme.colorScheme.onPrimaryContainer,
                  modifier = Modifier.padding(bottom = 4.dp))

              Text(
                  text =
                      if (suggestions.isNotEmpty()) description
                      else "Nothing to show for now, come back later!",
                  style = MaterialTheme.typography.bodyMedium,
                  color = MaterialTheme.colorScheme.onPrimaryContainer,
                  modifier = Modifier.padding(bottom = 8.dp))
            }

            Image(
                painter = painterResource(id = R.drawable.fred_no_bg_removebg_preview),
                contentDescription = "Decorative Leaf",
                modifier = Modifier.size(64.dp).align(Alignment.Top),
                contentScale = ContentScale.Fit)
          }

      if (suggestions.isEmpty()) return

      suggestions.forEachIndexed { index, s ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
              Checkbox(
                  checked = checkedStates[index],
                  onCheckedChange = { isChecked ->
                    if (!checkedStates[index]) {
                      onCheck(s)
                    }
                    checkedStates[index] = true // Update the state for the current checkbox
                  },
                  modifier = Modifier.size(32.dp),
                  colors =
                      CheckboxDefaults.colors(
                          checkedColor = MaterialTheme.colorScheme.primary,
                          uncheckedColor = MaterialTheme.colorScheme.onPrimaryContainer,
                          checkmarkColor = MaterialTheme.colorScheme.onPrimary))
              Spacer(modifier = Modifier.width(12.dp))
              Column {
                Text(
                    text = s.title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer)
                Text(
                    text = "Score: ${s.activity.impact} Freddies",
                    style = MaterialTheme.typography.bodySmall,
                    color =
                        MaterialTheme.colorScheme.onPrimaryContainer.copy(
                            alpha = 0.7f) // Slightly faded color
                    )
              }
            }
      }
    }
  }
}

package com.fred.app.presentation.onboarding.personal

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fred.app.R
import com.fred.app.data.repository.model.Avatar
import com.fred.app.data.repository.model.Diet
import com.fred.app.data.repository.model.Gender
import com.fred.app.data.repository.model.Location

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PersonalScreen(
    navController: NavController,
    onSubmit: (String, String, String, Int, Gender?) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PersonalViewModel = hiltViewModel(),
) {
  val context = LocalContext.current
  var name by remember { mutableStateOf("") }
  var username by remember { mutableStateOf("") }
  var email by remember { mutableStateOf("") }
  var age by remember { mutableStateOf("") }
  var selectedGender by remember { mutableStateOf<Gender?>(null) }
  var selectedDiet by remember { mutableStateOf<Diet?>(null) }
  var selectedAvatar by remember { mutableStateOf<Avatar?>(null) }
  var showAvatarDialog by remember { mutableStateOf(false) }

  var expanded by remember { mutableStateOf(false) }
  var selectedAddress by remember { mutableStateOf<Location?>(null) }
  var searchQuery by remember { mutableStateOf("") }

  val queriedLocations by viewModel.queriedLocations.collectAsState()

  val genderOptions = Gender.values().toList()
  val dietOptions = Diet.values().toList()
  val avatarOptions = Avatar.values().toList()

  Box(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
          Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Start,
              modifier = Modifier.padding(bottom = 32.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.fred_no_bg_removebg_preview),
                    contentDescription = "Profile Icon",
                    modifier = Modifier.size(64.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "User Information",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary)
              }

          // Avatar Selection
          Button(onClick = { showAvatarDialog = true }, modifier = Modifier.fillMaxWidth()) {
            Text(
                text = selectedAvatar?.name ?: "Choose Avatar",
                style = MaterialTheme.typography.bodyMedium)
          }

          // Name Input Field
          OutlinedTextField(
              value = name,
              onValueChange = { name = it },
              label = { Text("Name") },
              modifier = Modifier.fillMaxWidth())

          ExposedDropdownMenuBox(
              expanded = expanded && queriedLocations.isNotEmpty(),
              onExpandedChange = { expanded = it } // Toggle dropdown visibility
              ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                      searchQuery = it
                      viewModel.searchLocation(it)
                      expanded = true // Show dropdown when user starts typing
                    },
                    label = { Text("Address") },
                    modifier =
                        Modifier.menuAnchor() // Anchor the dropdown to this text field
                            .fillMaxWidth()
                            .testTag("inputTodoLocation"),
                    singleLine = true)

                // Dropdown menu for location suggestions
                // Another approach using DropdownMenu is in EditToDo.kt
                ExposedDropdownMenu(
                    expanded = expanded && queriedLocations.isNotEmpty(),
                    onDismissRequest = { expanded = false }) {
                      queriedLocations.filterNotNull().take(3).forEach { location ->
                        DropdownMenuItem(
                            text = {
                              Text(
                                  text =
                                      location.name.take(30) +
                                          if (location.name.length > 30) "..."
                                          else "", // Limit name length
                                  maxLines = 1 // Ensure name doesn't overflow
                                  )
                            },
                            onClick = {
                              selectedAddress = location
                              searchQuery = location.name
                              expanded = false // Close dropdown on selection
                            },
                            modifier = Modifier.padding(8.dp))
                      }

                      if (queriedLocations.size > 3) {
                        DropdownMenuItem(
                            text = { Text("More...") },
                            onClick = { /* Optionally show more results */},
                            modifier = Modifier.padding(8.dp))
                      }
                    }
              }

          // Other Input Fields
          OutlinedTextField(
              value = username,
              onValueChange = { username = it },
              label = { Text("Username") },
              modifier = Modifier.fillMaxWidth())

          OutlinedTextField(
              value = email,
              onValueChange = { email = it },
              label = { Text("Email") },
              modifier = Modifier.fillMaxWidth(),
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email))

          OutlinedTextField(
              value = age,
              onValueChange = { age = it },
              label = { Text("Age") },
              modifier = Modifier.fillMaxWidth(),
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))

          // Gender Selection with Chips
          Text(
              text = "Select Gender",
              style = MaterialTheme.typography.bodyMedium,
              color = MaterialTheme.colorScheme.onBackground,
              modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp))

          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            genderOptions.forEach { gender ->
              androidx.compose.material3.FilterChip(
                  selected = selectedGender == gender,
                  onClick = { selectedGender = gender },
                  label = { Text(gender.name) },
                  colors = androidx.compose.material3.FilterChipDefaults.filterChipColors())
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          // Avatar Selection Modal
          if (showAvatarDialog) {
            androidx.compose.material3.AlertDialog(
                onDismissRequest = { showAvatarDialog = false },
                title = {
                  Text(text = "Choose Your Avatar", style = MaterialTheme.typography.titleMedium)
                },
                text = {
                  Column {
                    avatarOptions.forEach { avatar ->
                      Row(
                          modifier =
                              Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable {
                                selectedAvatar = avatar
                                showAvatarDialog = false
                              },
                          verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = avatar.resourceId),
                                contentDescription = avatar.name,
                                modifier = Modifier.size(48.dp))
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = avatar.name, style = MaterialTheme.typography.bodyMedium)
                          }
                    }
                  }
                },
                confirmButton = {
                  Button(onClick = { showAvatarDialog = false }) { Text("Close") }
                })
          }

          Column(
              modifier = Modifier.fillMaxSize().padding(16.dp),
              verticalArrangement = Arrangement.Top,
              horizontalAlignment = Alignment.Start) {
                Text(
                    text = "Select Diet",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth())

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                  dietOptions.forEach { diet ->
                    androidx.compose.material3.FilterChip(
                        selected = selectedDiet == diet,
                        onClick = { selectedDiet = diet },
                        label = { Text(diet.name) },
                        colors = androidx.compose.material3.FilterChipDefaults.filterChipColors())
                  }
                }
              }

          // Submit Button
          Spacer(modifier = Modifier.height(32.dp))
          Button(
              onClick = {
                viewModel.registerUser(
                    username = username,
                    name = name,
                    mail = email,
                    avatarId = selectedAvatar?.name ?: Avatar.Leaf.name,
                    gender = selectedGender ?: Gender.Other,
                    address = selectedAddress ?: Location(),
                    diet = selectedDiet ?: Diet.Other,
                    age = age.toIntOrNull() ?: 0)
                navController.navigate("transports")
              },
              modifier = Modifier.fillMaxWidth(),
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
}

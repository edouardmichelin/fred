package com.fred.app.presentation.onboarding.personal

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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





@OptIn(ExperimentalLayoutApi::class)
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

    val genderOptions = Gender.values().toList()
    val dietOptions = Diet.values().toList()
    val avatarOptions = Avatar.values().toList()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fred_no_bg_removebg_preview),
                    contentDescription = "Profile Icon",
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "User Information",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Avatar Selection
            Button(
                onClick = { showAvatarDialog = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = selectedAvatar?.name ?: "Choose Avatar",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Name Input Field
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Other Input Fields
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Age") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Gender Selection with Chips
            Text(
                text = "Select Gender",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                genderOptions.forEach { gender ->
                    androidx.compose.material3.FilterChip(
                        selected = selectedGender == gender,
                        onClick = { selectedGender = gender },
                        label = { Text(gender.name) },
                        colors = androidx.compose.material3.FilterChipDefaults.filterChipColors()
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Avatar Selection Modal
            if (showAvatarDialog) {
                androidx.compose.material3.AlertDialog(
                    onDismissRequest = { showAvatarDialog = false },
                    title = {
                        Text(
                            text = "Choose Your Avatar",
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    text = {
                        Column {
                            avatarOptions.forEach { avatar ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp)
                                        .clickable {
                                            selectedAvatar = avatar
                                            showAvatarDialog = false
                                        },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = avatar.id),
                                        contentDescription = avatar.name,
                                        modifier = Modifier.size(48.dp)
                                    )
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Text(
                                        text = avatar.name,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    },
                    confirmButton = {
                        Button(onClick = { showAvatarDialog = false }) {
                            Text("Close")
                        }
                    }
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Select Diet",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    dietOptions.forEach { diet ->
                        androidx.compose.material3.FilterChip(
                            selected = selectedDiet == diet,
                            onClick = { selectedDiet = diet },
                            label = { Text(diet.name) },
                            colors = androidx.compose.material3.FilterChipDefaults.filterChipColors()
                        )
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
                        avatarId = selectedAvatar?.id ?: 0,
                        gender = selectedGender ?: Gender.Other,
                        address = Location(),
                        diet = selectedDiet ?: Diet.Other,
                    )
                    navController.navigate("transports")
                },
                modifier = Modifier.fillMaxWidth(),
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

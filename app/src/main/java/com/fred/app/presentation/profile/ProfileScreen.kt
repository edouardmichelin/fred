package com.fred.app.presentation.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.fred.app.R
import com.fred.app.ui.component.DefaultScaffold
import com.fred.app.ui.component.DefaultTextField
import com.fred.app.ui.component.ToolbarWithEndIcon
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    navigateToBack: () -> Unit,
) {
  val state by viewModel.uiState.collectAsState()

  val snackbarHostState = remember { SnackbarHostState() }
  LaunchedEffect(key1 = state.getUserError) {
    launch {
      if (state.getUserError.isNotEmpty()) snackbarHostState.showSnackbar(state.getUserError)
    }
  }

  // Prepare header end icon
  val endIcon =
      if (state.editMode) {
        R.drawable.ic_baseline_check_24
      } else {
        R.drawable.ic_baseline_edit_24
      }

  DefaultScaffold(
      topBar = {
        ToolbarWithEndIcon(
            title = "Profile",
            onBackPressClick = navigateToBack,
            endIconRes = endIcon,
            endIconClick = {
              if (state.editMode) {
                viewModel.triggerEvent(ProfileViewModel.ViewEvent.ApplyClick)
              } else {
                viewModel.triggerEvent(ProfileViewModel.ViewEvent.EditClick)
              }
            })
      },
      snackbarHostState = snackbarHostState) { padding ->
        Column(
            modifier = Modifier.padding(4.dp).padding(padding).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
              DefaultTextField(
                  modifier = Modifier.padding(vertical = 4.dp),
                  value = state.name,
                  iconVector = Icons.Default.Person,
                  iconText = "personIcon",
                  onValueChange = {
                    viewModel.triggerEvent(ProfileViewModel.ViewEvent.SetName(it))
                  },
                  label = "Name",
                  placeholder = "Name",
                  enabled = state.editMode)

              DefaultTextField(
                  modifier = Modifier.padding(vertical = 4.dp),
                  value = state.username,
                  iconVector = Icons.Default.Person,
                  iconText = "personIcon",
                  onValueChange = {
                    viewModel.triggerEvent(ProfileViewModel.ViewEvent.SetUsername(it))
                  },
                  label = "Username",
                  placeholder = "Username",
                  enabled = state.editMode)

              DefaultTextField(
                  modifier = Modifier.padding(vertical = 4.dp),
                  value = state.email,
                  iconVector = Icons.Default.Email,
                  iconText = "emailIcon",
                  onValueChange = {
                    viewModel.triggerEvent(ProfileViewModel.ViewEvent.SetEmail(it))
                  },
                  label = "Email",
                  placeholder = "Email",
                  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                  enabled = state.editMode)
/*
              DefaultTextField(
                  modifier = Modifier.padding(vertical = 4.dp),
                  value = state.address,
                  iconVector = Icons.Default.Home,
                  iconText = "homeIcon",
                  onValueChange = {
                    viewModel.triggerEvent(ProfileViewModel.ViewEvent.SetAddress(it))
                  },
                  label = "Address",
                  placeholder = "Address",
                  enabled = state.editMode)

 */
            }
      }
}

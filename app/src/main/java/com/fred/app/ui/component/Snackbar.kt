package com.fred.app.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import com.fred.app.R

@Composable
fun DefaultSnackBar(hostState: SnackbarHostState, status: SnackBarStatus) {
  SnackbarHost(hostState) {
    Snackbar(
        // elevation = 0.dp,
        // backgroundColor = Color(integerResource(id = status.backgroundColor)),
        snackbarData = it,
        shape = MaterialTheme.shapes.medium)
  }
}

sealed class SnackBarStatus(val backgroundColor: Int) {
  object SUCCESS : SnackBarStatus(R.color.black)

  object ERROR : SnackBarStatus(R.color.black)
}

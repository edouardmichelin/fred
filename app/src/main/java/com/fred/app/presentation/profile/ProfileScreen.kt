package com.fred.app.presentation.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fred.app.data.repository.model.Activity
import com.fred.app.presentation.home.IndeterminateCircularIndicator

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToBack: () -> Unit,
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val user by viewModel.user.collectAsState()

    if (isLoading || user == null) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            IndeterminateCircularIndicator()
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar and user information
        Image(
            painter = painterResource(id = user?.avatarId!!),
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 8.dp)
        )

        // Username and Score
        Text(
            text = user?.username!!,
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFFB78700),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Score: ${user?.score!!}",
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFFB78700),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Spacer between user info and activities
        Spacer(modifier = Modifier.height(16.dp))

        // Activities list
        Text(
            text = "Activities",
            style = MaterialTheme.typography.headlineSmall,
            color = Color(0xFFB78700),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(user?.activities!!) { activity ->
                ActivityItem(activity = activity)
            }
        }
    }
}

// A Composable function for individual activity items
@Composable
fun ActivityItem(activity: Activity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = activity.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Time: ${activity.timestamp}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

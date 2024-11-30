package com.fred.app.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.fred.app.R

@Composable
@ExperimentalComposeUiApi
fun PasswordView(
    value: String,
    placeholder: String,
    label: String,
    onValueChange: (String) -> Unit = {},
) {
    var passVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = "lockIcon"
            )
        },
        onValueChange = onValueChange,
        label = { Text(text = label) },
        placeholder = { Text(text = placeholder) },
        visualTransformation = if (passVisible) VisualTransformation.None
        else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passVisible) {
                R.drawable.ic_baseline_visibility_24
            } else {
                R.drawable.ic_baseline_visibility_off_24
            }
            val description = if (passVisible) "Hide password" else "Show password"

            IconButton(onClick = { passVisible = !passVisible }) {
                Icon(
                    painter = painterResource(id = image),
                    description
                )
            }
        }
    )
}
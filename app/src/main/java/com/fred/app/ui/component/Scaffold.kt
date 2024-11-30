package com.fred.app.ui.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun DefaultScaffold(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    snackBarHost: @Composable (SnackbarHostState) -> Unit = {
        DefaultSnackBar(
            hostState = snackbarHostState,
            status = SnackBarStatus.ERROR
        )
    },
    topBar: @Composable (() -> Unit) = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable (() -> Unit) = {},
    //backgroundColor: Color = MaterialTheme.colors.background,
    //contentColor: Color = contentColorFor(backgroundColor),
    loading: Boolean = false,
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = topBar,
        content = content,
        bottomBar = bottomBar,
        //contentColor = contentColor,
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = FabPosition.Center,
        //backgroundColor = backgroundColor
    )

    //Loading(visible = loading)
}
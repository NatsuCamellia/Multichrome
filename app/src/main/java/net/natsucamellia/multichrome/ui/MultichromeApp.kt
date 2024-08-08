package net.natsucamellia.multichrome.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.natsucamellia.multichrome.R
import net.natsucamellia.multichrome.ui.screens.HomeScreen
import net.natsucamellia.multichrome.ui.screens.MultichromeViewModel

@Composable
fun MultichromeApp() {
    val multichromeViewModel = viewModel<MultichromeViewModel>(factory = MultichromeViewModel.Factory)
    Scaffold(
        topBar = {
            TopAppBar()
        }
    ) {
        HomeScreen(
            multichromeViewModel,
            modifier = Modifier.padding(horizontal = 16.dp)
                .padding(it)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name)
            )
        },
        modifier = modifier
    )
}
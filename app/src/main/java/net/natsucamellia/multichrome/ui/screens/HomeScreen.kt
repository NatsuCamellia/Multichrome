package net.natsucamellia.multichrome.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material.icons.outlined.Casino
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.natsucamellia.multichrome.model.Palette
import net.natsucamellia.multichrome.ui.screens.MultichromeViewModel.MultichromeUiState
import net.natsucamellia.multichrome.ui.theme.Typography
import okhttp3.internal.toHexString
import java.util.Locale

@Composable
fun HomeScreen(
    multichromeViewModel: MultichromeViewModel,
    modifier: Modifier = Modifier
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        PaletteCard(
            palette = multichromeViewModel.palette,
            selectedIndex = multichromeViewModel.selectedIndex,
            toggleSelect = { multichromeViewModel.toggleSelect(it) },
        )
        ModelRow(
            models = multichromeViewModel.modelList,
            selectedModel = multichromeViewModel.selectedModel,
            updateSelectedModel = { multichromeViewModel.updateSelectedModel(it)},
        )
        ColorRow(
            palette = multichromeViewModel.palette,
            selectedIndex = multichromeViewModel.selectedIndex,
            toggleSelect = { multichromeViewModel.toggleSelect(it) },
        )
        Row {
            Spacer(modifier = Modifier.weight(1f))
            when (multichromeViewModel.multichromeUiState) {
                is MultichromeUiState.Success -> {
                    Button(
                        onClick = { multichromeViewModel.getPalette() }
                    ) {
                        Icon(imageVector = Icons.Outlined.Casino, contentDescription = null)
                        Text(text = "Generate")
                    }
                }
                is MultichromeUiState.Loading -> {
                    Button(
                        enabled = false,
                        onClick = { multichromeViewModel.getPalette() }
                    ) {
                        Icon(imageVector = Icons.Outlined.Casino, contentDescription = null)
                        Text(text = "Loading")
                    }
                }
                is MultichromeUiState.Error -> {
                    Button(
                        onClick = { multichromeViewModel.getPalette() }
                    ) {
                        Icon(imageVector = Icons.Outlined.Refresh, contentDescription = null)
                        Text(text = "Retry")
                    }
                }
            }
        }
    }
}

@Composable
fun PaletteCard(
    palette: Palette,
    selectedIndex: List<Boolean>,
    toggleSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card (
        modifier = modifier
    ) {
        Row {
            palette.colors.mapIndexed { index, it ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(it)
                        .height(192.dp)
                        .weight(1f)
                        .clickable {
                            toggleSelect(index)
                        }
                ) {
                    if (selectedIndex[index]) {
                        Icon(imageVector = Icons.Outlined.Lock, contentDescription = null)
                    }
                }
            }
        }
        Column (
            modifier = Modifier.padding(16.dp)
        ) {
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Random Palette", style = Typography.titleLarge)
                Text(text = palette.model.toCamelCase(), style = Typography.labelLarge, fontWeight = FontWeight.Bold)
            }
            palette.colors.map {
                Text(text = it.hex(), fontFamily = FontFamily.Monospace)
            }
        }
    }
}

@Composable
fun ModelRow(
    models: List<String>,
    selectedModel: String,
    updateSelectedModel: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(text = "Model", style = Typography.labelLarge)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(models) {
                FilterChip(
                    selected = it == selectedModel,
                    onClick = { updateSelectedModel(it) },
                    label = { Text(text = it.toCamelCase()) },
                    leadingIcon = {
                        if (it == selectedModel) {
                            Icon(imageVector = Icons.Outlined.Check, contentDescription = null)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ColorRow(
    palette: Palette,
    selectedIndex: List<Boolean>,
    toggleSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(text = "Selected Colors", style = Typography.labelLarge)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(palette.colors) { index, it ->
                FilterChip(
                    selected = selectedIndex[index],
                    onClick = { toggleSelect(index) },
                    label = { Text(
                        text = it.toArgb().toHexString().substring(2, 8).uppercase(Locale.getDefault())
                    )},
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Tag,
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }
}

fun String.toCamelCase(): String {
    return this.split('_').joinToString(separator = " ") {
        it.replaceFirstChar { word -> if (word.isLowerCase()) word.titlecase(Locale.US) else word.toString() }
    }
}

fun Color.hex(): String {
    return "#" + this.toArgb().toHexString().substring(2, 8).uppercase(Locale.getDefault())
}
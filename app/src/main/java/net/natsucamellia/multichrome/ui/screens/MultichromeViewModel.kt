package net.natsucamellia.multichrome.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import net.natsucamellia.multichrome.MultichromeApplication
import net.natsucamellia.multichrome.data.MultichromeRepository
import net.natsucamellia.multichrome.model.Palette
import java.io.IOException

class MultichromeViewModel(
    private val multichromeRepository: MultichromeRepository
) : ViewModel() {
    var selectedModel: String by mutableStateOf("default")
        private set
    var modelList: List<String> by mutableStateOf(listOf("default"))
    var palette: Palette by mutableStateOf(Palette.Empty)
    var selectedIndex: List<Boolean> by mutableStateOf(listOf(false, false, false, false, false))
    var multichromeUiState: MultichromeUiState by mutableStateOf(MultichromeUiState.Loading)
        private set

    init {
        getModelList()
        getRandomPalette()
    }

    fun updateSelectedModel(model: String) {
        selectedModel = model
    }

    private fun getModelList() {
        multichromeUiState = MultichromeUiState.Loading
        viewModelScope.launch {
            modelList = try {
                multichromeRepository.getModelList()
            } catch (e: IOException) {
                Log.e("getModelList", "Failed to get model list.")
                listOf()
            }
        }
    }

    private fun getRandomPalette() {
        multichromeUiState = MultichromeUiState.Loading
        viewModelScope.launch {
            multichromeUiState = try {
                palette = multichromeRepository.getRandomPalette(selectedModel)
                MultichromeUiState.Success(palette)
            } catch (e: IOException) {
                e.printStackTrace()
                MultichromeUiState.Error
            }
        }
    }

    fun getPalette() {
        multichromeUiState = MultichromeUiState.Loading
        viewModelScope.launch {
            multichromeUiState = try {
                palette = multichromeRepository.getPaletteWithSuggestion(selectedModel, palette, selectedIndex)
                MultichromeUiState.Success(palette)
            } catch (e: IOException) {
                e.printStackTrace()
                MultichromeUiState.Error
            }
        }
    }

    fun toggleSelect(index: Int) {
        val updatedSelectedIndex = selectedIndex.toMutableList()
        updatedSelectedIndex[index] = !selectedIndex[index]
        selectedIndex = updatedSelectedIndex.toList()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MultichromeApplication)
                val multichromeRepository = application.container.multichromeRepository
                MultichromeViewModel(multichromeRepository)
            }
        }
    }

    sealed interface MultichromeUiState {
        data class Success(val palette: Palette): MultichromeUiState
        data object Error: MultichromeUiState
        data object Loading: MultichromeUiState
    }
}
package co.edu.udea.compumovil.gr02_20242.lab1

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ViewModel containing the app data and methods to process the data
 */
class Lab1ViewModel : ViewModel() {
    // Lab1 UI state
    private val _uiState = MutableStateFlow(Lab1UiState())
    val uiState: StateFlow<Lab1UiState> = _uiState.asStateFlow()

    var name by mutableStateOf("")
        private set

    // Update the name
    fun updateName(nameUpdate: String){
        name = nameUpdate
    }
}
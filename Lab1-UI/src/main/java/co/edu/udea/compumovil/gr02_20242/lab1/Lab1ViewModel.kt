package co.edu.udea.compumovil.gr02_20242.lab1

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel containing the app data and methods to process the data
 */
class Lab1ViewModel : ViewModel() {
    // Lab1 UI state
    private val _uiState = MutableStateFlow(Lab1UiState())
    val uiState: StateFlow<Lab1UiState> = _uiState.asStateFlow()

    var name by mutableStateOf("")
        private set
    var lastName by mutableStateOf("")
        private set
    var sex by mutableStateOf("")
        private set
    var birthdate by mutableStateOf("")
        private set
    var scholarity by mutableStateOf("")
        private set
    var phone by mutableStateOf("")
        private set
    var address by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var country by mutableStateOf("")
        private set
    var city by mutableStateOf("")
        private set

    fun updateName(nameUpdate: String){
        name = nameUpdate
    }
    fun updateLastName(lastNameUpdate: String){
        lastName = lastNameUpdate
    }
    fun updateSex(sexUpdate: String){
        sex = sexUpdate
    }
    fun updateBirthdate(birthdateUpdate: String){
        birthdate = birthdateUpdate
    }
    fun updateScholarity(scholarityUpdate: String){
        scholarity = scholarityUpdate
    }
    fun updatePhone(phoneUpdate: String){
        phone = phoneUpdate
    }
    fun updateAddress(addressUpdate: String){
        address = addressUpdate
    }
    fun updateEmail(emailUpdate: String){
        email = emailUpdate
    }
    fun updateCountry(countryUpdate: String){
        country = countryUpdate
    }
    fun updateCity(cityUpdate: String){
        city = cityUpdate
    }

    fun handleSubmitPersonalData(): String{
        if(name.isEmpty() || lastName.isEmpty() || birthdate.isEmpty()){
            return "Error"
        } else {
            _uiState.update { currentState ->
                currentState.copy(name = name, lastName = lastName, sex = sex, birthdate = birthdate, scholarity = scholarity)
            }
            Log.d("mensaje", "Nombre ${_uiState.value.name}")
            Log.d("mensaje", "Apellido ${_uiState.value.lastName}")
            Log.d("mensaje", "Sexo ${_uiState.value.sex}")
            Log.d("mensaje", "Fecha de nacimiento ${_uiState.value.birthdate}")
            Log.d("mensaje", "Grado académico ${_uiState.value.scholarity}")
            return "Success"
        }
    }

    fun handleSubmitContactData(): String{
        if(name.isEmpty() || lastName.isEmpty() || birthdate.isEmpty()){
            Log.d("mensaje", "Nombre ${_uiState.value.name}")
            Log.d("mensaje", "Apellido ${_uiState.value.lastName}")
            Log.d("mensaje", "Sexo ${_uiState.value.sex}")
            Log.d("mensaje", "Fecha de nacimiento ${_uiState.value.birthdate}")
            Log.d("mensaje", "Grado académico ${_uiState.value.scholarity}")
            return "Error"
        } else {
            Log.d("mensaje", "Nombre ${_uiState.value.name}")
            Log.d("mensaje", "Apellido ${_uiState.value.lastName}")
            Log.d("mensaje", "Sexo ${_uiState.value.sex}")
            Log.d("mensaje", "Fecha de nacimiento ${_uiState.value.birthdate}")
            Log.d("mensaje", "Grado académico ${_uiState.value.scholarity}")
            return "Success"
        }
    }

}
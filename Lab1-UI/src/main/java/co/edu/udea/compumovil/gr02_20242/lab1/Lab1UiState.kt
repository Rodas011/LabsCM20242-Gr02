package co.edu.udea.compumovil.gr02_20242.lab1

/**
 * Data class that represents the app UI state
 */
data class Lab1UiState (
    val name: String = "",
    val lastName: String = "",
    val sex: String = "",
    val birthdate: String = "",
    val scholarity: String = "",
    val phone: String = "",
    val address: String = "",
    val email: String = "",
    val country: String = "",
    val city: String = "",
    val isPersonalDataWrong: Boolean = false,
)
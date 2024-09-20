package co.edu.udea.compumovil.gr02_20242.lab1

data class CityRequest(val country: String)

data class CityResponse(
    val error: Boolean,
    val msg: String,
    val data: List<String>
)
package co.edu.udea.compumovil.gr02_20242.lab1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ContactDataScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: Lab1ViewModel,
    submissionStatus: String
){
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ContactDataLayout(
                onContinueClicked = onContinueClicked,
                submissionStatus = submissionStatus,
                phone = viewModel.phone,
                address = viewModel.address,
                email = viewModel.email,
                country = viewModel.country,
                city = viewModel.city,
                cities = viewModel.cities,
                onPhoneChanged = { viewModel.updatePhone(it) },
                onAddressChanged = { viewModel.updateAddress(it) },
                onEmailChanged = { viewModel.updateEmail(it) },
                onCountryChanged = { viewModel.updateCountry(it) },
                onCityChanged = { viewModel.updateCity(it) },
                snackbarHostState = snackbarHostState,
                viewModel = viewModel,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun ContactDataLayout(
    submissionStatus: String,
    phone: String,
    address: String,
    email: String,
    country: String,
    city: String,
    cities: List<String>,
    onContinueClicked: () -> Unit,
    onPhoneChanged: (String) -> Unit,
    onAddressChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onCountryChanged: (String) -> Unit,
    onCityChanged: (String) -> Unit,
    snackbarHostState: SnackbarHostState,
    viewModel: Lab1ViewModel,
    modifier: Modifier = Modifier
) {
    val latinAmericanCountries = listOf(
        "Argentina", "Bolivia", "Brazil", "Chile", "Colombia", "Costa Rica", "Cuba",
        "Ecuador", "El Salvador", "Guatemala", "Honduras", "Mexico", "Nicaragua", "Panama",
        "Paraguay", "Peru", "Dominican Republic", "Uruguay", "Venezuela"
    )

    val colombianCities = listOf(
        "Bogotá", "Medellín", "Cali", "Barranquilla", "Cartagena", "Cúcuta", "Bucaramanga",
        "Pereira", "Santa Marta", "Ibagué", "Manizales", "Neiva", "Villavicencio", "Pasto",
        "Armenia", "Montería", "Valledupar", "Popayán", "Sincelejo", "Riohacha"
    )

    var isPhoneTouched by remember { mutableStateOf(false) }
    var isAddressTouched by remember { mutableStateOf(false) }
    var isEmailTouched by remember { mutableStateOf(false) }
    var isCountryTouched by remember { mutableStateOf(false) }
    var isCityTouched by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.contact_info),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        OutlinedTextField(
            value = phone,
            onValueChange = onPhoneChanged,
            label = { Text(stringResource(R.string.phone)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next),

            isError = isPhoneTouched && phone.isBlank(),
            supportingText = { if (isPhoneTouched && phone.isBlank()) Text(stringResource(R.string.required)) else null },
            modifier = Modifier.fillMaxWidth()
                .onFocusChanged { focusState ->
                    isPhoneTouched = focusState.isFocused || focusState.hasFocus
                }
        )

        OutlinedTextField(
            value = address,
            onValueChange = onAddressChanged,
            label = { Text(stringResource(R.string.address)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(2.dp))

        OutlinedTextField(
            value = email,
            onValueChange = onEmailChanged,
            label = { Text(stringResource(R.string.email)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            isError = isEmailTouched && email.isBlank(),
            supportingText = { if (isEmailTouched && email.isBlank()) Text(stringResource(R.string.required)) else null },
            modifier = Modifier.fillMaxWidth()
                .onFocusChanged { focusState ->
                    isEmailTouched = focusState.isFocused || focusState.hasFocus
                }
        )

        CountryDropdown(
            country,
            latinAmericanCountries,
            onCountryChanged,
            isError = isCountryTouched && country.isBlank(), // Check if country is blank
            supportingText = { if (isCountryTouched && country.isBlank()) Text(stringResource(R.string.required)) else null },
            modifier = Modifier.onFocusChanged { focusState ->
                    isEmailTouched = focusState.isFocused || focusState.hasFocus
                }
        )

        CityDropdown(city, cities, onCityChanged)
        Spacer(modifier = Modifier.height(6.dp))

        Button(
            onClick = onContinueClicked,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = stringResource(R.string._continue))
        }

        if (submissionStatus.isNotEmpty()) {
            val snackbarMessage = if (submissionStatus == "Success") {
                stringResource(R.string.success_message)
            } else {
                stringResource(R.string.error_message)
            }
            if (submissionStatus == "Error") {
                isPhoneTouched = true
                isAddressTouched = true
                isEmailTouched = true
                isCountryTouched = true
                isCityTouched = true
            }
            LaunchedEffect(submissionStatus) {
                snackbarHostState.showSnackbar(
                    message = snackbarMessage,
                    duration = SnackbarDuration.Short
                )
                snackbarHostState.currentSnackbarData?.dismiss()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDropdown(
    country: String,
    countries: List<String>,
    onCountrySelected: (String) -> Unit,
    isError: Boolean,
    supportingText: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var filteredCountries by remember { mutableStateOf(countries) }
    var selectedText by remember { mutableStateOf(country) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { text ->
                selectedText = text
                filteredCountries = countries
                    .filter { it.startsWith(text, ignoreCase = true) }
                    .sorted()
            },
            label = { Text(stringResource(R.string.country)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            isError = isError,
            supportingText = supportingText,
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            filteredCountries.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        selectedText = item
                        expanded = false
                        onCountrySelected(item)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityDropdown(
    city: String,
    cities: List<String>,
    onCitySelected: (String) -> Unit)
{
    var expanded by remember { mutableStateOf(false) }
    var filteredCities by remember { mutableStateOf(cities) }
    var selectedText by remember { mutableStateOf(city) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { text ->
                selectedText = text
                filteredCities = cities
                    .filter { it.startsWith(text, ignoreCase = true) }
                    .sorted()
            },
            label = { Text(stringResource(R.string.city)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            filteredCities.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        selectedText = item
                        expanded = false
                        onCitySelected(item)
                    }
                )
            }
        }
    }
}
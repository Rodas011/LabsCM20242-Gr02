package co.edu.udea.compumovil.gr02_20242.lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.edu.udea.compumovil.gr02_20242.lab1.ui.theme.Labs20242Gr02Theme


class ContactDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Labs20242Gr02Theme {
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
                    Form2Screen(snackbarHostState = snackbarHostState)
                }
                }
            }
        }
    }
}


@Composable
fun Form2Screen(lab1ViewModel: Lab1ViewModel = viewModel(), snackbarHostState: SnackbarHostState) {

    val lab1UiState by lab1ViewModel.uiState.collectAsState()
    var submissionStatus by rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    Form2Layout(
        uiState = lab1UiState,
        onAddressChange = { lab1ViewModel.updateAddress(it) },
        onEmailChange = { lab1ViewModel.updateEmail(it) },
        onCountryChange = { lab1ViewModel.updateCountry(it) },
        onCityChange = { lab1ViewModel.updateCity(it) },
        onPhoneChange = { lab1ViewModel.updatePhone(it) },
         onSubmit = {
            submissionStatus = lab1ViewModel.handleSubmitContactData()
            if (submissionStatus == "Success") {
                keyboardController?.hide()
            }
        },
       // submissionStatus = submissionStatus,
        snackbarHostState = snackbarHostState,
        lab1ViewModel = lab1ViewModel,
        modifier = Modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Form2Layout(
    uiState: Lab1UiState,
    onPhoneChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onCountryChange: (String) -> Unit,
    onCityChange: (String) -> Unit,
    onSubmit: () -> Unit,
    // submissionStatus: String,
    snackbarHostState: SnackbarHostState,
    lab1ViewModel: Lab1ViewModel = viewModel(),
    modifier: Modifier = Modifier
) {

    var submissionStatus by rememberSaveable { mutableStateOf("") }
    val latinAmericanCountries = listOf(
        "Argentina", "Bolivia", "Brasil", "Chile", "Colombia", "Costa Rica", "Cuba",
        "Ecuador", "El Salvador", "Guatemala", "Honduras", "México", "Nicaragua", "Panamá",
        "Paraguay", "Perú", "República Dominicana", "Uruguay", "Venezuela"
    )

    val colombianCities = listOf(
        "Bogotá", "Medellín", "Cali", "Barranquilla", "Cartagena", "Cúcuta", "Bucaramanga",
        "Pereira", "Santa Marta", "Ibagué", "Manizales", "Neiva", "Villavicencio", "Pasto",
        "Armenia", "Montería", "Valledupar", "Popayán", "Sincelejo", "Riohacha"
    )

        Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            Text(
                text = "Información de contacto",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(start = 10.dp)
            )


            OutlinedTextField(
                value = uiState.phone,
                onValueChange = onPhoneChange,
                label = { Text(stringResource(R.string.phone)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next),

                isError = uiState.phone.isBlank(),
                supportingText = { if (uiState.phone.isBlank()) Text(stringResource(R.string.required)) else null },
                modifier = Modifier.fillMaxWidth()
            )



            OutlinedTextField(
                value = uiState.address,
                onValueChange = onAddressChange,
                label = { Text(stringResource(R.string.address)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )


            OutlinedTextField(
                value = uiState.email,
                onValueChange = onEmailChange,
                label = { Text(stringResource(R.string.email)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                isError = uiState.email.isBlank(),
                supportingText = { if (uiState.email.isBlank()) Text(stringResource(R.string.required)) else null },
                modifier = Modifier.fillMaxWidth()
            )


            CountryDropdown(latinAmericanCountries,
                onCountryChange,
                isError = uiState.country.isBlank(), // Check if country is blank
                supportingText = { if (uiState.country.isBlank()) Text(stringResource(R.string.required)) else null }
            )

            CityDropdown(colombianCities, onCityChange)
            Spacer(modifier = Modifier.height(6.dp))

            Button(onClick = { submissionStatus = lab1ViewModel.handleSubmitPersonalData() }, modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(R.string.siguiente))
            }
            if (submissionStatus.isNotEmpty()) {
                val snackbarMessage = if (submissionStatus == "Success") {
                    stringResource(R.string.success_message)
                } else {
                    stringResource(R.string.error_message)
                }
                LaunchedEffect(submissionStatus) {
                    snackbarHostState.showSnackbar(
                        message = snackbarMessage,
                        duration = SnackbarDuration.Short
                    )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDropdown(
    countries: List<String>,
    onCountrySelected: (String) -> Unit,
    isError: Boolean,
    supportingText: @Composable (() -> Unit)?
) {
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf("") }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.country)) },
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
            countries.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        selected = item
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
fun CityDropdown(cities: List<String>, onCitySelected: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf("") }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selected,
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.city)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            cities.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        selected = item
                        expanded = false
                        onCitySelected(item)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun ContactDataScreenPreview() {
    Labs20242Gr02Theme {
        val snackbarHostState = remember { SnackbarHostState() }
        Form2Screen(snackbarHostState = snackbarHostState)
    }
}


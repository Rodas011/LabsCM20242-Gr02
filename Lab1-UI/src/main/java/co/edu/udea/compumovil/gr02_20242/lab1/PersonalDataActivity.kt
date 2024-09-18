package co.edu.udea.compumovil.gr02_20242.lab1

import android.app.DatePickerDialog
import android.content.res.Configuration
import android.widget.DatePicker
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import java.util.Calendar

@Composable
fun PersonalDataScreen(
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
            PersonalDataLayout(
                onContinueClicked = onContinueClicked,
                submissionStatus = submissionStatus,
                name = viewModel.name,
                lastName = viewModel.lastName,
                sex = viewModel.sex,
                birthdate = viewModel.birthdate,
                scholarity = viewModel.scholarity,
                onNameChanged = { viewModel.updateName(it) },
                onLastNameChanged = { viewModel.updateLastName(it) },
                onSexChanged = { viewModel.updateSex(it) },
                onBirthdateChanged = { viewModel.updateBirthdate(it) },
                onScholarityChanged = { viewModel.updateScholarity(it) },
                snackbarHostState = snackbarHostState,
                viewModel = viewModel,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun PersonalDataLayout(
    onContinueClicked: () -> Unit,
    submissionStatus: String,
    name: String,
    lastName: String,
    sex: String,
    birthdate: String,
    scholarity: String,
    onNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onSexChanged: (String) -> Unit,
    onBirthdateChanged: (String) -> Unit,
    onScholarityChanged: (String) -> Unit,
    snackbarHostState: SnackbarHostState,
    viewModel: Lab1ViewModel,
    modifier: Modifier = Modifier
){
    val configuration = LocalConfiguration.current

    var manSelected by rememberSaveable { mutableStateOf(false) }
    var womanSelected by rememberSaveable { mutableStateOf(false) }
    var sexSelected by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val SexOptions = listOf("Hombre", "Mujer")
    val Scholarities = listOf("Primaria", "Bachillerato", "Universidad")

    var isNameTouched by remember { mutableStateOf(false) }
    var isLastNameTouched by remember { mutableStateOf(false) }
    var isSexTouched by remember { mutableStateOf(false) }
    var isBirthdateTouched by remember { mutableStateOf(false) }
    var isScholarityTouched by remember { mutableStateOf(false) }


    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Formulario",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = modifier
                    .padding(horizontal = 16.dp)
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = onNameChanged,
                    label = { Text(stringResource(R.string.name)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next),

                    isError = isNameTouched && name.isBlank(),
                    supportingText = { if (isNameTouched && name.isBlank()) Text(stringResource(R.string.required)) else null },
                    modifier = Modifier.width(300.dp).padding(end = 16.dp)
                        .onFocusChanged { focusState ->
                            isNameTouched = focusState.isFocused || focusState.hasFocus
                        }
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = lastName,
                    onValueChange = onLastNameChanged,
                    label = { Text(stringResource(R.string.last_name)) },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next),

                    isError = isLastNameTouched && lastName.isBlank(),
                    supportingText = { if (isLastNameTouched && lastName.isBlank()) Text(stringResource(R.string.required)) else null },
                    modifier = Modifier.width(300.dp).padding(end = 16.dp)
                        .onFocusChanged { focusState ->
                            isLastNameTouched = focusState.isFocused || focusState.hasFocus
                        }
                )
            }
            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = modifier
                    .padding(horizontal = 16.dp)
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Sexo:",
                    style = MaterialTheme.typography.bodyLarge.merge(),
                    modifier = Modifier.padding(start = 150.dp)
                )

                SexOptions.forEach { option ->

                    RadioButton(
                        selected = (option == sex),
                        onClick = { onSexChanged(option) },
                        modifier = Modifier.padding(start = 16.dp)
                    )

                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyLarge.merge(),
                        modifier = Modifier.padding(start = 1.dp)
                    )
                }

            }

            Spacer(modifier = Modifier.height(6.dp))

            val datePickerDialog = DatePickerDialog(
                context,
                { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                    // Actualizar la fecha seleccionada
                    onBirthdateChanged("$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear")
                }, year, month, day
            )

            Row(
                modifier = modifier
                    .padding(horizontal = 150.dp)
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Fecha de nacimiento:",
                    style = MaterialTheme.typography.bodyLarge.merge()
                )

                Button(
                    onClick = { datePickerDialog.show() },
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Text(text = "Cambiar")
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            var expanded by rememberSaveable() { mutableStateOf(false) }

            Box(modifier = Modifier.width(300.dp).padding(start = 16.dp)) {

                OutlinedButton(onClick = { expanded = true }) {
                    Text(
                        text = scholarity,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.weight(
                            1f,
                        ),
                    )
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)

                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    Scholarities.forEach {
                        DropdownMenuItem(text = {
                            Text(text = it)
                        }, onClick = {
                            expanded = false
                            onScholarityChanged(it)
                        })
                    }
                }
            }

            Spacer(modifier = Modifier.height(3.dp))

            Button(
                onClick = onContinueClicked,
                modifier = Modifier.width(150.dp).align(Alignment.End),
            ) {
                Text(stringResource(R.string._continue))
            }

            if (manSelected) {
                viewModel.updateSex("Hombre")
            }
            if (womanSelected) {
                viewModel.updateSex("Mujer")
            }

            if (submissionStatus.isNotEmpty()) {
                val snackbarMessage = if (submissionStatus == "Success") {
                    stringResource(R.string.success_message)
                } else {
                    stringResource(R.string.error_message)
                }
                if (submissionStatus == "Error") {
                    isNameTouched = true
                    isLastNameTouched = true
                    isSexTouched = true
                    isBirthdateTouched = true
                    isScholarityTouched = true
                }
                LaunchedEffect(submissionStatus) {
                    snackbarHostState.showSnackbar(
                        message = snackbarMessage,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Formulario",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(start = 110.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = onNameChanged,
                label = { Text(stringResource(R.string.name)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next),

                isError = isNameTouched && name.isBlank(),
                supportingText = { if (isNameTouched && name.isBlank()) Text(stringResource(R.string.required)) else null },
                modifier = Modifier.fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isNameTouched = focusState.isFocused || focusState.hasFocus
                    }
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = lastName,
                onValueChange = onLastNameChanged,
                label = { Text(stringResource(R.string.last_name)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next),

                isError = isLastNameTouched && lastName.isBlank(),
                supportingText = { if (isLastNameTouched && lastName.isBlank()) Text(stringResource(R.string.required)) else null },
                modifier = Modifier.fillMaxWidth()
                    .onFocusChanged { focusState ->
                        isLastNameTouched = focusState.isFocused || focusState.hasFocus
                    }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = modifier
                    .padding(horizontal = 16.dp)
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Sexo:",
                    style = MaterialTheme.typography.bodyLarge.merge(),
                    modifier = Modifier.padding(start = 1.dp)
                )

                SexOptions.forEach { option ->

                    RadioButton(
                        selected = (option == sex),
                        onClick = { onSexChanged(option) },
                        modifier = Modifier.padding(start = 16.dp)
                    )

                    Text(
                        text = option,
                        style = MaterialTheme.typography.bodyLarge.merge(),
                        modifier = Modifier.padding(start = 1.dp)
                    )
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            val datePickerDialog = DatePickerDialog(
                context,
                { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
                    // Actualizar la fecha seleccionada
                    onBirthdateChanged("$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear")
                }, year, month, day
            )

            Row(
                modifier = modifier
                    .padding(horizontal = 16.dp)
                    .height(56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Fecha de nacimiento:",
                    style = MaterialTheme.typography.bodyLarge.merge()
                )

                Button(
                    onClick = { datePickerDialog.show() },
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Text(text = "Cambiar")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            var expanded by rememberSaveable() { mutableStateOf(false) }

            Box(modifier = Modifier.fillMaxWidth()) {

                OutlinedButton(onClick = { expanded = true }) {
                    Text(
                        text = scholarity,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.weight(
                            1f,
                        ),
                    )
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)

                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    Scholarities.forEach {
                        DropdownMenuItem(text = {
                            Text(text = it)
                        }, onClick = {
                            expanded = false
                            onScholarityChanged(it)
                        })
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onContinueClicked,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string._continue))
            }

            if (manSelected) {
                viewModel.updateSex("Hombre")
            }
            if (womanSelected) {
                viewModel.updateSex("Mujer")
            }

            if (submissionStatus.isNotEmpty()) {
                val snackbarMessage = if (submissionStatus == "Success") {
                    stringResource(R.string.success_message)
                } else {
                    stringResource(R.string.error_message)
                }
                if (submissionStatus == "Error") {
                    isNameTouched = true
                    isLastNameTouched = true
                    isSexTouched = true
                    isBirthdateTouched = true
                    isScholarityTouched = true
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
}